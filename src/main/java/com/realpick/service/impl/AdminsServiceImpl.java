package com.realpick.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.realpick.dao.AdminsMapper;
import com.realpick.entity.Admins;
import com.realpick.service.AdminsService;
import com.realpick.utils.MD5Utils;
import com.realpick.vo.ResultVO;
import com.realpick.vo.StatusCode;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 管理员 服务实现类
 * </p>
 *
 * @author pocoalegre
 * @since 2021-10-22
 */
@Service
public class AdminsServiceImpl extends ServiceImpl<AdminsMapper, Admins> implements AdminsService {

    @Autowired
    private AdminsMapper adminsMapper;

    @Override
    public ResultVO adminRegist(String name, String pwd) {

        //查询管理员名是否已存在
        HashMap<String, Object> columnMap = new HashMap<>();
        columnMap.put("admin_name", name);
        List<Admins> adminList = adminsMapper.selectByMap(columnMap);

        //如果没有注册，则保存
        if (adminList.size() == 0) {
            Admins admin = new Admins();
            admin.setAdminName(name);
            String md5Pwd = MD5Utils.md5(pwd + name);
            admin.setAdminPwd(md5Pwd);
            admin.setAdminNickname(name);
            admin.setAdminImg("headDefault.jpg");

            //注册
            int insert = adminsMapper.insert(admin);
            if (insert == 1) {
                return new ResultVO(StatusCode.OK, "注册成功！", null);
            } else {
                return new ResultVO(StatusCode.NO, "注册失败！", null);
            }
        } else {
            return new ResultVO(StatusCode.NO, "管理员名已存在！", null);
        }
    }

    @Override
    public ResultVO adminLogin(String name, String pwd) {

        //查询管理员名是否已存在
        HashMap<String, Object> columnMap = new HashMap<>();
        columnMap.put("admin_name", name);
        List<Admins> adminList = adminsMapper.selectByMap(columnMap);

        //如果存在，判断密码是否正确
        if (adminList.size() == 0) {
            return new ResultVO(StatusCode.NO, "该管理员账号不存在！", null);
        } else {
            String md5Pwd = MD5Utils.md5(pwd + name);
            if (md5Pwd.equals(adminList.get(0).getAdminPwd())) {

                //校验密码成功，生成token
                JwtBuilder builder = Jwts.builder();
                String token = builder.setSubject(name)    //token数据
                        .setIssuedAt(new Date())    //token生成时间
                        .setId(adminList.get(0).getAdminId() + "")    //设置管理员id为tokenid
                        .setExpiration(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000))    //设置过期时间（一天）
                        .signWith(SignatureAlgorithm.HS256, "realpick")   //设置加密方式和加密密码
                        .compact();

                //创建map返回管理员信息和token
                HashMap<Object, Object> returnMap = new HashMap<>();
                returnMap.put("admin", adminList.get(0));
                returnMap.put("token", token);
                return new ResultVO(StatusCode.OK, "登陆成功！", returnMap);
            } else {
                return new ResultVO(StatusCode.NO, "密码错误！", null);
            }
        }
    }

    @Override
    public ResultVO adminListById(Integer id) {
        HashMap<String, Object> columnMap = new HashMap<>();
        columnMap.put("admin_id", id);
        try {
            List<Admins> adminList = adminsMapper.selectByMap(columnMap);
            return new ResultVO(StatusCode.OK, "获取信息成功！", adminList);
        } catch (Exception e) {
            System.out.println(e);
            return new ResultVO(StatusCode.NO, "获取信息失败！", null);
        }

    }

    @Override
    public ResultVO adminModify(Admins admin) {

        //判断是否输入相同昵称
        Admins adminById = adminsMapper.selectById(admin.getAdminId());
        if (admin.getAdminNickname().equals(adminById.getAdminNickname())) {

            int update = adminsMapper.updateById(admin);
            if (update == 1) {
                Admins adminReturn = adminsMapper.selectById(admin.getAdminId());
                return new ResultVO(StatusCode.OK, "修改成功！", adminReturn);
            } else {
                return new ResultVO(StatusCode.NO, "修改失败！", null);
            }
        } else {

            //判断管理员昵称是否与他人重名
            HashMap<String, Object> columnMap = new HashMap<>();
            columnMap.put("admin_nickname", admin.getAdminNickname());
            List<Admins> adminList = adminsMapper.selectByMap(columnMap);

            //如果不重名，则修改
            if (adminList.size() == 0) {
                int update = adminsMapper.updateById(admin);
                if (update == 1) {
                    Admins adminReturn = adminsMapper.selectById(admin.getAdminId());
                    return new ResultVO(StatusCode.OK, "修改成功！", adminReturn);
                } else {
                    return new ResultVO(StatusCode.NO, "修改失败！", null);
                }
            } else {
                return new ResultVO(StatusCode.NO, "管理员昵称已存在！", null);
            }
        }
    }

    @Override
    public ResultVO adminPwdModify(Integer id, String pwd, String newPwd) {

        //当前用户密码与输入密码进行比较
        Admins adminBefore = adminsMapper.selectById(id);
        String md5PwdBefore = MD5Utils.md5(pwd + adminBefore.getAdminName());
        if (md5PwdBefore.equals(adminBefore.getAdminPwd())) {
            String md5Pwd = MD5Utils.md5(newPwd + adminBefore.getAdminName());

            //不能修改为相同密码
            if (md5Pwd.equals(adminBefore.getAdminPwd())) {
                return new ResultVO(StatusCode.NO, "新密码不能与原密码相同！", null);
            } else {
                Admins admin = new Admins();
                admin.setAdminId(id);
                admin.setAdminPwd(md5Pwd);

                //修改
                int update = adminsMapper.updateById(admin);
                if (update == 1) {
                    return new ResultVO(StatusCode.OK, "修改成功，请重新登录！", null);
                } else {
                    return new ResultVO(StatusCode.NO, "修改失败！", null);
                }
            }
        } else {
            return new ResultVO(StatusCode.NO, "密码输入错误！", null);
        }
    }

    @Override
    public ResultVO adminList(String queryType, String queryInfo, Integer pageNum, Integer pageSize) {

        //分页设置
        PageHelper.startPage(pageNum, pageSize);

        //查询条件封装
        QueryWrapper<Admins> qw = new QueryWrapper<>();
        if (!queryType.equals("")) {

            //判断是否有查询条件
            if (queryType.equals("admin_id")) {
                if (!queryInfo.equals("")) {
                    try {
                        qw.eq(queryType, Integer.valueOf(queryInfo));
                    } catch (Exception e) {
                        System.out.println(e);
                        return new ResultVO(StatusCode.NO, "请输入合法的编号！", null);
                    }
                }
            } else {
                if (!queryInfo.equals("")) {
                    qw.like(queryType, queryInfo);
                }
            }
        }

        //查询列表并分页
        try {
            List<Admins> adminList = adminsMapper.selectList(qw);
            PageInfo<Admins> adminPageInfo = new PageInfo<>(adminList);
            return new ResultVO(StatusCode.OK, "获取列表成功！", adminPageInfo);
        } catch (Exception e) {
            System.out.println(e);
            return new ResultVO(StatusCode.NO, "获取列表失败！", null);
        }
    }

    @Override
    public ResultVO adminDelete(Integer id) {

        //删除
        int delete = adminsMapper.deleteById(id);
        if (delete == 1) {
            return new ResultVO(StatusCode.OK, "删除成功！", null);
        } else {
            return new ResultVO(StatusCode.NO, "删除失败！", null);
        }
    }

    @Override
    public ResultVO adminImgModify(Integer id, String imgName) {

        //根据id更改头像
        Admins admin = new Admins();
        admin.setAdminId(id);
        admin.setAdminImg(imgName);

        //修改
        int update = adminsMapper.updateById(admin);
        if (update == 1) {
            return new ResultVO(StatusCode.OK, "修改头像成功！", imgName);
        } else {
            return new ResultVO(StatusCode.NO, "修改头像失败！", null);
        }
    }
}
