package com.realpick.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.realpick.entity.OrderVO;
import com.realpick.entity.Orders;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 订单 Mapper 接口
 * </p>
 *
 * @author pocoalegre
 * @since 2021-10-22
 */

@Repository
public interface OrdersMapper extends BaseMapper<Orders> {

    //根据用户查询订单VO
    List<OrderVO> orderVO(Integer userId);

    //月度销量统计
    List<Map<Object, Object>> monthSalesCount();

    //类型销售前五统计
    List<Map<Object, Object>> salesFiveCount();

}
