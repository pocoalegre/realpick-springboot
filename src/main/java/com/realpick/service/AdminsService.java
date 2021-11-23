package com.realpick.service;

import com.realpick.entity.Admins;
import com.baomidou.mybatisplus.extension.service.IService;
import com.realpick.vo.ResultVO;

/**
 * <p>
 * 管理员 服务类
 * </p>
 *
 * @author pocoalegre
 * @since 2021-10-22
 */
public interface AdminsService extends IService<Admins> {

    //注册
    ResultVO adminRegist(String name, String pwd);

    //登录
    ResultVO adminLogin(String name, String pwd);

    //管理员自查信息
    ResultVO adminListById(Integer id);

    //修改信息
    ResultVO adminModify(Admins admin);

    //修改密码
    ResultVO adminPwdModify(Integer id,String pwd, String newPwd);

    //管理员列表
    ResultVO adminList(String queryType, String queryInfo, Integer pageNum, Integer pageSize);

    //删除管理员
    ResultVO adminDelete(Integer id);

    //修改头像
    ResultVO adminImgModify(Integer id, String imgName);

}
