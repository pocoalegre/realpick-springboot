package com.realpick.service;

import com.realpick.entity.Admins;
import com.realpick.entity.Users;
import com.baomidou.mybatisplus.extension.service.IService;
import com.realpick.vo.ResultVO;

/**
 * <p>
 * 用户 服务类
 * </p>
 *
 * @author pocoalegre
 * @since 2021-10-22
 */
public interface UsersService extends IService<Users> {

    //注册
    ResultVO userRegist(String name, String pwd);

    //登录
    ResultVO userLogin(String name, String pwd);

    //用户自查信息
    ResultVO userById(Integer id);

    //修改信息
    ResultVO userModify(Users user);

    //修改密码
    ResultVO userPwdModify(Integer id,String pwd, String newPwd);

    //用户修改密码
    ResultVO userPwdModifyByAdmin(Integer id, String newPwd);

    //用户列表
    ResultVO userList(String queryType, String queryInfo, Integer pageNum, Integer pageSize);

    //删除用户
    ResultVO userDelete(Integer id);

    //修改头像
    ResultVO userImgModify(Integer id, String imgName);

}
