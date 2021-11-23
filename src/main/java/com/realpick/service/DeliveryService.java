package com.realpick.service;

import com.realpick.entity.Delivery;
import com.baomidou.mybatisplus.extension.service.IService;
import com.realpick.vo.ResultVO;

/**
 * <p>
 * 快递 服务类
 * </p>
 *
 * @author pocoalegre
 * @since 2021-10-22
 */
public interface DeliveryService extends IService<Delivery> {

    //添加快递
    ResultVO addDelivery(Delivery delivery);

    //修改快递
    ResultVO modifyDelivery(Delivery delivery);

    //删除快递
    ResultVO deleteDelivery(Integer id);

    //快递列表
    ResultVO deliveryList(String queryCom, String queryType, String queryInfo, Integer pageNum, Integer pageSize);

    //单条快递
    ResultVO deliveryById(Integer id);

}
