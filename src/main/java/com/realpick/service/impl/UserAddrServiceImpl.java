package com.realpick.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.realpick.dao.UserAddrMapper;
import com.realpick.entity.UserAddr;
import com.realpick.service.UserAddrService;
import com.realpick.vo.ResultVO;
import com.realpick.vo.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 用户地址 服务实现类
 * </p>
 *
 * @author pocoalegre
 * @since 2021-10-22
 */
@Service
public class UserAddrServiceImpl extends ServiceImpl<UserAddrMapper, UserAddr> implements UserAddrService {

    @Autowired
    private UserAddrMapper userAddrMapper;

    @Override
    public ResultVO userAddrList(Integer id, Integer pageNum, Integer pageSize) {

        //分页设置
        PageHelper.startPage(pageNum, pageSize);

        //查询条件封装
        QueryWrapper<UserAddr> qw = new QueryWrapper<>();
        qw.eq("user_id", id);

        //查询列表并分页
        try {
            List<UserAddr> userAddrList = userAddrMapper.selectList(qw);
            PageInfo<UserAddr> userAddrPageInfo = new PageInfo<>(userAddrList);
            return new ResultVO(StatusCode.OK, "获取列表成功！", userAddrPageInfo);
        } catch (Exception e) {
            System.out.println(e);
            return new ResultVO(StatusCode.NO, "获取列表失败！", null);
        }
    }

    @Override
    public ResultVO userAddrById(Integer id) {
        try {
            UserAddr userAddr = userAddrMapper.selectById(id);
            return new ResultVO(StatusCode.OK, "获取信息成功！", userAddr);
        } catch (Exception e) {
            System.out.println(e);
            return new ResultVO(StatusCode.NO, "获取信息失败！", null);
        }
    }

    @Override
    public ResultVO addUserAddr(UserAddr userAddr) {

        //查询是否有地址
        Integer userId = userAddr.getUserId();
        HashMap<String, Object> columnMap1 = new HashMap<>();
        columnMap1.put("user_id", userId);
        List<UserAddr> userAddrList1 = userAddrMapper.selectByMap(columnMap1);

        //如果没有地址，则第一个地址设置为默认
        if (userAddrList1.size() == 0) {
            if (userAddr.getIsMain() != 0) {
                int insert = userAddrMapper.insert(userAddr);
                if (insert == 1) {
                    return new ResultVO(StatusCode.OK, "添加成功！", null);
                } else {
                    return new ResultVO(StatusCode.NO, "添加失败！", null);
                }
            } else {
                return new ResultVO(StatusCode.NO, "必须有默认地址！", null);
            }
        } else {
            if (userAddr.getIsMain() != 0) {

                //查询默认地址
                HashMap<String, Object> columnMap2 = new HashMap<>();
                columnMap2.put("user_id", userId);
                columnMap2.put("is_main", 1);
                List<UserAddr> userAddrList2 = userAddrMapper.selectByMap(columnMap2);

                //如果有默认地址，添加地址为默认地址时，则更新默认地址
                int insert = userAddrMapper.insert(userAddr);
                if (insert == 1) {

                    //更新默认地址
                    userAddrList2.get(0).setIsMain(0);
                    userAddrMapper.updateById(userAddrList2.get(0));
                    return new ResultVO(StatusCode.OK, "添加成功！", null);
                } else {
                    return new ResultVO(StatusCode.NO, "添加失败！", null);
                }
            } else {
                int insert = userAddrMapper.insert(userAddr);
                if (insert == 1) {
                    return new ResultVO(StatusCode.OK, "添加成功！", null);
                } else {
                    return new ResultVO(StatusCode.NO, "添加失败！", null);
                }
            }

        }
    }

    @Override
    public ResultVO modifyUserAddr(UserAddr userAddr) {

        //查询是否修改唯一地址
        Integer userId = userAddr.getUserId();
        HashMap<String, Object> columnMap1 = new HashMap<>();
        columnMap1.put("user_id", userId);
        List<UserAddr> userAddrList1 = userAddrMapper.selectByMap(columnMap1);

        //如果当前修改为唯一地址
        if (userAddrList1.size() == 1) {
            if (userAddr.getIsMain() != 0) {
                int update = userAddrMapper.updateById(userAddr);
                if (update == 1) {
                    return new ResultVO(StatusCode.OK, "修改成功！", null);
                } else {
                    return new ResultVO(StatusCode.NO, "修改失败！", null);
                }
            } else {
                return new ResultVO(StatusCode.NO, "必须有默认地址！", null);
            }
        } else {

            //查询默认地址
            HashMap<String, Object> columnMap2 = new HashMap<>();
            columnMap2.put("user_id", userId);
            columnMap2.put("is_main", 1);
            List<UserAddr> userAddrList2 = userAddrMapper.selectByMap(columnMap2);

            //如果当前修改地址为默认地址，则无法修改默认
            if (userAddr.getAddrId().equals(userAddrList2.get(0).getAddrId())) {
                userAddr.setIsMain(1);
                int update = userAddrMapper.updateById(userAddr);
                if (update == 1) {
                    return new ResultVO(StatusCode.OK, "修改成功！", null);
                } else {
                    return new ResultVO(StatusCode.NO, "修改失败！", null);
                }
            } else {

                //如果非默认地址，修改为默认地址，则更新默认地址
                if (userAddr.getIsMain() == 1) {
                    int update = userAddrMapper.updateById(userAddr);
                    if (update == 1) {

                        //更新默认地址
                        userAddrList2.get(0).setIsMain(0);
                        userAddrMapper.updateById(userAddrList2.get(0));
                        return new ResultVO(StatusCode.OK, "修改成功！", null);
                    } else {
                        return new ResultVO(StatusCode.NO, "修改失败！", null);
                    }
                } else {
                    int update = userAddrMapper.updateById(userAddr);
                    if (update == 1) {
                        return new ResultVO(StatusCode.OK, "修改成功！", null);
                    } else {
                        return new ResultVO(StatusCode.NO, "修改失败！", null);
                    }
                }
            }
        }
    }

    @Override
    public ResultVO deleteUserAddr(Integer id) {

        UserAddr userAddr = userAddrMapper.selectById(id);
        if (userAddr.getIsMain() == 1) {
            return new ResultVO(StatusCode.NO, "默认地址无法删除！", null);
        } else {
            int delete = userAddrMapper.deleteById(id);
            if (delete == 1) {
                return new ResultVO(StatusCode.OK, "删除成功！", null);
            } else {
                return new ResultVO(StatusCode.NO, "删除失败！", null);
            }
        }
    }
}
