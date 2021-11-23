package com.realpick.service;

import com.realpick.entity.OrderDetail;
import com.baomidou.mybatisplus.extension.service.IService;
import com.realpick.vo.ResultVO;

/**
 * <p>
 * 订单详情 服务类
 * </p>
 *
 * @author pocoalegre
 * @since 2021-10-22
 */
public interface OrderDetailService extends IService<OrderDetail> {

    //订单细节列表
    ResultVO orderDetailList(String queryOrderId, Integer pageNum, Integer pageSize);

}
