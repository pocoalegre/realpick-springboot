package com.realpick.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.realpick.dao.UsersMapper;
import com.realpick.entity.Users;
import com.realpick.service.UsersService;
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
 * 用户 服务实现类
 * </p>
 *
 * @author pocoalegre
 * @since 2021-10-22
 */
@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users> implements UsersService {

    @Autowired
    private UsersMapper usersMapper;

    @Override
    public ResultVO userRegist(String name, String pwd) {

        //查询用户名是否已存在
        HashMap<String, Object> columnMap = new HashMap<>();
        columnMap.put("user_name", name);
        List<Users> userList = usersMapper.selectByMap(columnMap);

        //如果没有注册，则保存
        if (userList.size() == 0) {
            Users user = new Users();
            user.setUserName(name);
            String md5Pwd = MD5Utils.md5(pwd + name);
            user.setUserPwd(md5Pwd);
            user.setUserNickname(name);
            user.setUserImg("headDefault.jpg");
            user.setUserGender("");

            //注册
            int insert = usersMapper.insert(user);
            if (insert == 1) {
                return new ResultVO(StatusCode.OK, "注册成功！", null);
            } else {
                return new ResultVO(StatusCode.NO, "注册失败！", null);
            }
        } else {
            return new ResultVO(StatusCode.NO, "用户名已存在！", null);
        }
    }

    @Override
    public ResultVO userLogin(String name, String pwd) {

        //查询用户名是否已存在
        HashMap<String, Object> columnMap = new HashMap<>();
        columnMap.put("user_name", name);
        List<Users> userList = usersMapper.selectByMap(columnMap);

        //如果存在，判断密码是否正确
        if (userList.size() == 0) {
            return new ResultVO(StatusCode.NO, "该用户不存在！", null);
        } else {
            String md5Pwd = MD5Utils.md5(pwd + name);
            if (md5Pwd.equals(userList.get(0).getUserPwd())) {

                //校验密码成功，生成token
                JwtBuilder builder = Jwts.builder();
                String token = builder.setSubject(name)    //token数据
                        .setIssuedAt(new Date())    //token生成时间
                        .setId(userList.get(0).getUserId() + "")    //设置用户id为tokenid
                        .setExpiration(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000))    //设置过期时间（一天）
                        .signWith(SignatureAlgorithm.HS256, "realpick")   //设置加密方式和加密密码
                        .compact();

                //创建map返回用户信息和token
                HashMap<Object, Object> returnMap = new HashMap<>();
                returnMap.put("user", userList.get(0));
                returnMap.put("token", token);
                return new ResultVO(StatusCode.OK, "登陆成功！", returnMap);
            } else {
                return new ResultVO(StatusCode.NO, "密码错误！", null);
            }
        }
    }

    @Override
    public ResultVO userById(Integer id) {
        try {
            Users user = usersMapper.selectById(id);
            return new ResultVO(StatusCode.OK, "获取信息成功！", user);
        } catch (Exception e) {
            System.out.println(e);
            return new ResultVO(StatusCode.NO, "获取信息失败！", null);
        }

    }

    @Override
    public ResultVO userModify(Users user) {

        //判断是否输入相同昵称
        Users userById = usersMapper.selectById(user.getUserId());
        if (user.getUserNickname().equals(userById.getUserNickname())) {
            int update = usersMapper.updateById(user);
            if (update == 1) {
                Users userReturn = usersMapper.selectById(user.getUserId());
                return new ResultVO(StatusCode.OK, "修改成功！", userReturn);
            } else {
                return new ResultVO(StatusCode.NO, "修改失败！", null);
            }
        } else {

            //判断用户昵称是否与他人重名
            HashMap<String, Object> columnMap = new HashMap<>();
            columnMap.put("user_nickname", user.getUserNickname());
            List<Users> userList = usersMapper.selectByMap(columnMap);

            //如果不重名，则修改
            if (userList.size() == 0) {
                int update = usersMapper.updateById(user);
                if (update == 1) {
                    Users userReturn = usersMapper.selectById(user.getUserId());
                    return new ResultVO(StatusCode.OK, "修改成功！", userReturn);
                } else {
                    return new ResultVO(StatusCode.NO, "修改失败！", null);
                }
            } else {
                return new ResultVO(StatusCode.NO, "用户昵称已存在！", null);
            }
        }
    }

    @Override
    public ResultVO userPwdModify(Integer id, String pwd, String newPwd) {

        //当前用户密码与输入密码进行比较
        Users userBefore = usersMapper.selectById(id);
        String md5PwdBefore = MD5Utils.md5(pwd + userBefore.getUserName());
        if (md5PwdBefore.equals(userBefore.getUserPwd())) {
            String md5Pwd = MD5Utils.md5(newPwd + userBefore.getUserName());

            //不能修改为相同密码
            if (md5Pwd.equals(userBefore.getUserPwd())) {
                return new ResultVO(StatusCode.NO, "新密码不能与原密码相同！", null);
            } else {
                Users user = new Users();
                user.setUserId(id);
                user.setUserPwd(md5Pwd);
                int update = usersMapper.updateById(user);
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
    public ResultVO userPwdModifyByAdmin(Integer id, String newPwd) {

        Users userBefore = usersMapper.selectById(id);
        String md5Pwd = MD5Utils.md5(newPwd + userBefore.getUserName());

        //不能修改为相同密码
        if (md5Pwd.equals(userBefore.getUserPwd())) {
            return new ResultVO(StatusCode.NO, "新密码不能与原密码相同！", null);
        } else {
            Users user = new Users();
            user.setUserId(id);
            user.setUserPwd(md5Pwd);
            int update = usersMapper.updateById(user);
            if (update == 1) {
                return new ResultVO(StatusCode.OK, "修改成功！", null);
            } else {
                return new ResultVO(StatusCode.NO, "修改失败！", null);
            }
        }
    }

    @Override
    public ResultVO userList(String queryType, String queryInfo, Integer pageNum, Integer pageSize) {

        //分页设置
        PageHelper.startPage(pageNum, pageSize);

        //查询条件封装
        QueryWrapper<Users> qw = new QueryWrapper<>();
        if (!queryType.equals("")) {

            //判断是否有查询条件
            if (queryType.equals("user_id")) {
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
            List<Users> userList = usersMapper.selectList(qw);
            PageInfo<Users> userPageInfo = new PageInfo<>(userList);
            return new ResultVO(StatusCode.OK, "获取列表成功！", userPageInfo);
        } catch (Exception e) {
            System.out.println(e);
            return new ResultVO(StatusCode.NO, "获取列表失败！", null);
        }
    }

    @Override
    public ResultVO userDelete(Integer id) {
        int delete = usersMapper.deleteById(id);
        if (delete == 1) {
            return new ResultVO(StatusCode.OK, "删除成功！", null);
        } else {
            return new ResultVO(StatusCode.NO, "删除失败！", null);
        }
    }

    @Override
    public ResultVO userImgModify(Integer id, String imgName) {

        //根据id更改头像
        Users user = new Users();
        user.setUserId(id);
        user.setUserImg(imgName);

        //性别年龄电话邮箱
        Users byId = usersMapper.selectById(id);
        user.setUserGender(byId.getUserGender());
        user.setUserAge(byId.getUserAge());
        user.setUserTel(byId.getUserTel());
        user.setUserEmail(byId.getUserEmail());

        int update = usersMapper.updateById(user);
        if (update == 1) {
            return new ResultVO(StatusCode.OK, "修改头像成功！", imgName);
        } else {
            return new ResultVO(StatusCode.NO, "修改头像失败！", null);
        }
    }
}
