package com.realpick.dao;

import com.realpick.entity.Orders;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

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

}
