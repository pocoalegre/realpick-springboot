package com.realpick.service;

import com.realpick.entity.UserAddr;
import com.baomidou.mybatisplus.extension.service.IService;
import com.realpick.vo.ResultVO;

/**
 * <p>
 * 用户地址 服务类
 * </p>
 *
 * @author pocoalegre
 * @since 2021-10-22
 */
public interface UserAddrService extends IService<UserAddr> {

    //地址列表
    ResultVO userAddrList(Integer id, Integer pageNum, Integer pageSize);

    //单条地址
    ResultVO userAddrById(Integer id);

    //添加地址
    ResultVO addUserAddr(UserAddr userAddr);

    //修改地址
    ResultVO modifyUserAddr(UserAddr userAddr);

    //删除地址
    ResultVO deleteUserAddr(Integer id);

    //地址列表
    ResultVO userAddrListAll(Integer id);

}
