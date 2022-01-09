package com.realpick.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.realpick.dao.DeliveryMapper;
import com.realpick.dao.OrdersMapper;
import com.realpick.entity.Delivery;
import com.realpick.entity.Orders;
import com.realpick.service.DeliveryService;
import com.realpick.vo.ResultVO;
import com.realpick.vo.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 快递 服务实现类
 * </p>
 *
 * @author pocoalegre
 * @since 2021-10-22
 */
@Service
public class DeliveryServiceImpl extends ServiceImpl<DeliveryMapper, Delivery> implements DeliveryService {

    @Autowired
    private DeliveryMapper deliveryMapper;

    @Autowired
    private OrdersMapper ordersMapper;

    @Override
    public ResultVO addDelivery(Delivery delivery) {

        //查询是否有该订单
        Integer orderId = delivery.getOrderId();
        HashMap<String, Object> columnMap = new HashMap<>();
        columnMap.put("order_id", orderId);
        List<Orders> ordersList = ordersMapper.selectByMap(columnMap);
        if (ordersList.size() == 0) {
            return new ResultVO(StatusCode.NO, "该订单编号不存在！", null);
        }

        //添加
        int insert = deliveryMapper.insert(delivery);
        if (insert == 1) {
            return new ResultVO(StatusCode.OK, "添加成功！", null);
        } else {
            return new ResultVO(StatusCode.NO, "添加失败！", null);
        }
    }

    @Override
    public ResultVO modifyDelivery(Delivery delivery) {

        //查询是否有该订单
        Integer orderId = delivery.getOrderId();
        HashMap<String, Object> columnMap = new HashMap<>();
        columnMap.put("order_id", orderId);
        List<Orders> ordersList = ordersMapper.selectByMap(columnMap);
        if (ordersList.size() == 0) {
            return new ResultVO(StatusCode.NO, "该订单编号不存在！", null);
        }

        //修改
        int update = deliveryMapper.updateById(delivery);
        if (update == 1) {
            return new ResultVO(StatusCode.OK, "修改成功！", null);
        } else {
            return new ResultVO(StatusCode.NO, "修改失败！", null);
        }
    }

    @Override
    public ResultVO deleteDelivery(Integer id) {
        int delete = deliveryMapper.deleteById(id);
        if (delete == 1) {
            return new ResultVO(StatusCode.OK, "删除成功！", null);
        } else {
            return new ResultVO(StatusCode.NO, "删除失败！", null);
        }
    }

    @Override
    public ResultVO deliveryList(String queryCom, String queryType, String queryInfo, Integer pageNum, Integer pageSize) {

        //分页设置
        PageHelper.startPage(pageNum, pageSize);

        //查询条件封装
        QueryWrapper<Delivery> qw = new QueryWrapper<>();

        //判断是否有查询条件
        if (!queryCom.equals("")) {
            qw.eq("delivery_com", queryCom);
        }
        if (!queryType.equals("")) {
            if (queryType.equals("order_id")) {
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
            List<Delivery> deliveryList = deliveryMapper.selectList(qw);
            PageInfo<Delivery> deliveryPageInfo = new PageInfo<>(deliveryList);
            return new ResultVO(StatusCode.OK, "获取列表成功！", deliveryPageInfo);
        } catch (Exception e) {
            return new ResultVO(StatusCode.NO, "获取列表失败！", null);
        }

    }

    @Override
    public ResultVO deliveryById(Integer id) {
        try {
            Delivery delivery = deliveryMapper.selectById(id);
            return new ResultVO(StatusCode.OK, "获取信息成功！", delivery);
        } catch (Exception e) {
            System.out.println(e);
            return new ResultVO(StatusCode.NO, "获取信息失败！", null);
        }
    }

}
