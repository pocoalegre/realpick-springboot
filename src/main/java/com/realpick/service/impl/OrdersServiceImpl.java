package com.realpick.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.realpick.dao.OrderDetailMapper;
import com.realpick.dao.OrdersMapper;
import com.realpick.dao.ProductMapper;
import com.realpick.entity.OrderDetail;
import com.realpick.entity.OrderVO;
import com.realpick.entity.Orders;
import com.realpick.entity.Product;
import com.realpick.service.OrdersService;
import com.realpick.vo.ResultVO;
import com.realpick.vo.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author pocoalegre
 * @since 2021-10-22
 */
@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements OrdersService {

    @Autowired
    private OrdersMapper ordersMapper;

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Autowired
    private ProductMapper productMapper;

    @Override
    public ResultVO orderList(String queryType, String queryInfo, Integer pageNum, Integer pageSize) {

        //分页设置
        PageHelper.startPage(pageNum, pageSize);

        //查询条件封装
        QueryWrapper<Orders> qw = new QueryWrapper<>();

        //判断是否有查询条件
        if (!queryType.equals("")) {
            if (queryType.equals("user_id")) {
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
            List<Orders> OrderList = ordersMapper.selectList(qw);
            PageInfo<Orders> OrderPageInfo = new PageInfo<>(OrderList);
            return new ResultVO(StatusCode.OK, "获取列表成功！", OrderPageInfo);
        } catch (Exception e) {
            System.out.println(e);
            return new ResultVO(StatusCode.NO, "获取列表失败！", null);
        }
    }

    @Override
    public ResultVO orderById(Integer id) {
        try {
            Orders order = ordersMapper.selectById(id);
            return new ResultVO(StatusCode.OK, "获取信息成功！", order);
        } catch (Exception e) {
            System.out.println(e);
            return new ResultVO(StatusCode.NO, "获取信息失败！", null);
        }
    }

    @Override
    public ResultVO deleteOrder(Integer id) {

        HashMap<String, Object> columnMap = new HashMap<>();
        columnMap.put("order_id", id);

        //删除订单相关信息
        try {
            orderDetailMapper.deleteByMap(columnMap);
            int delete = ordersMapper.deleteById(id);
            if (delete == 1) {
                return new ResultVO(StatusCode.OK, "删除成功！", null);
            } else {
                return new ResultVO(StatusCode.NO, "删除失败！", null);
            }
        } catch (Exception e) {
            System.out.println(e);
            return new ResultVO(StatusCode.NO, "删除异常！", null);
        }
    }

    @Override
    public ResultVO modifyOrder(Orders order) {

        //代发货或者发货期间 可以修改收货信息
        if (order.getStatus() == 2 || order.getStatus() == 3){
            int update = ordersMapper.updateById(order);
            if (update == 1) {
                return new ResultVO(StatusCode.OK, "修改成功！", null);
            } else {
                return new ResultVO(StatusCode.NO, "修改失败！", null);
            }
        }else {
            return new ResultVO(StatusCode.NO, "修改失败！", null);
        }

    }

    @Override
    public ResultVO addOrder(Orders order) {

        //设置订单编号
        String orderNumber = UUID.randomUUID().toString().replace("-", "").toUpperCase().substring(0,20);
        order.setOrderNumber(orderNumber);

        //设置提交时间
        order.setSubmitTime(new Date());
        int insert = ordersMapper.insert(order);
        if (insert == 1) {
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("orderId", order.getOrderId());
            resultMap.put("orderNumber", order.getOrderNumber());
            return new ResultVO(StatusCode.OK, "添加成功！", resultMap);
        } else {
            return new ResultVO(StatusCode.NO, "添加失败！", null);
        }
    }

    @Override
    public ResultVO orderVOList(Integer pageNum, Integer pageSize, Integer userId) {

        //分页设置
        PageHelper.startPage(pageNum, pageSize);

        try {
            //查询列表并分页
            List<OrderVO> orderVOList = ordersMapper.orderVO(userId);
            PageInfo<OrderVO> orderVOPageInfo = new PageInfo<>(orderVOList);
            return new ResultVO(StatusCode.OK, "获取信息成功！", orderVOPageInfo);
        } catch (Exception e) {
            System.out.println(e);
            return new ResultVO(StatusCode.NO, "获取信息失败！", null);
        }
    }

    @Override
    public ResultVO cancelOrder(Integer id) {

        //设置订单状态
        Orders order = new Orders();
        order.setOrderId(id);
        order.setStatus(0);
        order.setCancelTime(new Date());
        order.setFinishTime(new Date());

        int update = ordersMapper.updateById(order);
        if (update == 1){

            //获取订单细节
            Map<String, Object> map = new HashMap<>();
            map.put("order_id", id);
            OrderDetail orderDetail = orderDetailMapper.selectByMap(map).get(0);

            //查询商品
            Product productBefore = productMapper.selectById(orderDetail.getProductId());

            //还原库存
            Product product = new Product();
            product.setProductId(productBefore.getProductId());
            product.setProductStock(productBefore.getProductStock() + orderDetail.getBuyNumber());
            productMapper.updateById(product);

            return new ResultVO(StatusCode.OK, "取消成功！", null);
        }else {
            return new ResultVO(StatusCode.NO, "取消失败！", null);
        }
    }

    @Override
    public ResultVO confirmOrder(Integer id) {

        //设置订单状态
        Orders order = new Orders();
        order.setOrderId(id);
        order.setStatus(4);
        order.setReceiveTime(new Date());
        order.setFinishTime(new Date());

        int update = ordersMapper.updateById(order);
        if (update == 1){
            return new ResultVO(StatusCode.OK, "确认成功！", null);
        }else {
            return new ResultVO(StatusCode.NO, "确认异常！", null);
        }
    }

    @Override
    public void paySuccess(String orderNumber, String total_amount) {

        Map<String, Object> map = new HashMap<>();
        map.put("order_number", orderNumber);

        //转换金额
        BigDecimal actualAmount = new BigDecimal(total_amount);

        //设置付款完成
        List<Orders> ordersList = ordersMapper.selectByMap(map);
        Orders order = ordersList.get(0);

        //设置商品销量
        Map<String, Object> map1 = new HashMap<>();
        map1.put("order_id", order.getOrderId());
        List<OrderDetail> orderDetailList = orderDetailMapper.selectByMap(map1);
        Integer productId = orderDetailList.get(0).getProductId();
        Integer buyNumber = orderDetailList.get(0).getBuyNumber();
        Product product = productMapper.selectById(productId);
        Product productModify = new Product();
        productModify.setProductId(productId);
        productModify.setProductSales(product.getProductSales() + buyNumber);
        productMapper.updateById(productModify);

        order.setStatus(2);
        order.setPayType(1);
        order.setPayTime(new Date());
        order.setActualAmount(actualAmount);
        ordersMapper.updateById(order);
    }

    @Override
    public ResultVO orderCount() {

        //查询条件封装
        QueryWrapper<Orders> qw = new QueryWrapper<>();

        //查询条件
        qw.ne("status", 0);

        try {
            List<Orders> orderList = ordersMapper.selectList(qw);

            //统计订单量
            int count = orderList.size();
            return new ResultVO(StatusCode.OK, "获取订单量成功！", count);
        } catch (Exception e) {
            System.out.println(e);
            return new ResultVO(StatusCode.NO, "获取订单量失败！", null);
        }
    }

    @Override
    public ResultVO saleAmount() {

        //查询条件封装
        QueryWrapper<Orders> qw = new QueryWrapper<>();

        //查询条件
        qw.eq("status", 4);

        try {
            List<Orders> orderList = ordersMapper.selectList(qw);

            //统计总金额
            BigDecimal saleAmount = new BigDecimal(0);
            for (Orders order : orderList) {
                saleAmount = saleAmount.add(order.getActualAmount());
            }
            return new ResultVO(StatusCode.OK, "获取销售额成功！", saleAmount);
        } catch (Exception e) {
            System.out.println(e);
            return new ResultVO(StatusCode.NO, "获取销售额失败！", null);
        }
    }

//    @Override
//    public ResultVO monthSalesCount() {
//        return null;
//    }
//
//    @Override
//    public ResultVO salesFirstFiveCount() {
//        return null;
//    }
}
