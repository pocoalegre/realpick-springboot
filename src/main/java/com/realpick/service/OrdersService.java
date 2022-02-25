package com.realpick.service;

import com.realpick.entity.Orders;
import com.baomidou.mybatisplus.extension.service.IService;
import com.realpick.vo.ResultVO;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author pocoalegre
 * @since 2021-10-22
 */
public interface OrdersService extends IService<Orders> {

    //订单列表
    ResultVO orderList(String queryType, String queryInfo, Integer pageNum, Integer pageSize);

    //单条查询
    ResultVO orderById(Integer id);

    //删除订单
    ResultVO deleteOrder(Integer id);

    //修改订单
    ResultVO modifyOrder(Orders order);

    //添加订单
    ResultVO addOrder(Orders order);

    //用户订单列表
    ResultVO orderVOList(Integer pageNum, Integer pageSize, Integer userId);

    //取消订单
    ResultVO cancelOrder(Integer id);

    //收货
    ResultVO confirmOrder(Integer id);

    //付款成功
    void paySuccess(String orderNumber, String total_amount);

    //统计订单量
    ResultVO orderCount();

    //统计销售额
    ResultVO saleAmount();

    //月度销量统计
    ResultVO monthSalesCount();

    //类型销售前五统计
    ResultVO salesFiveCount();

}
