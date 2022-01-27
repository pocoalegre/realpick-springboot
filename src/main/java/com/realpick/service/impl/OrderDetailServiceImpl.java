package com.realpick.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.realpick.dao.OrderDetailMapper;
import com.realpick.dao.ProductMapper;
import com.realpick.entity.OrderDetail;
import com.realpick.entity.Product;
import com.realpick.service.OrderDetailService;
import com.realpick.vo.ResultVO;
import com.realpick.vo.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 订单详情 服务实现类
 * </p>
 *
 * @author pocoalegre
 * @since 2021-10-22
 */
@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Autowired
    private ProductMapper productMapper;

    @Override
    public ResultVO orderDetailList(String queryOrderId, Integer pageNum, Integer pageSize) {

        //分页设置
        PageHelper.startPage(pageNum, pageSize);

        //查询条件封装
        QueryWrapper<OrderDetail> qw = new QueryWrapper<>();

        //判断是否有查询条件
        if (!queryOrderId.equals("")) {
            qw.eq("order_id", Integer.valueOf(queryOrderId));
        }

        //查询列表并分页
        try {
            List<OrderDetail> OrderDetailList = orderDetailMapper.selectList(qw);
            PageInfo<OrderDetail> OrderDetailPageInfo = new PageInfo<>(OrderDetailList);
            return new ResultVO(StatusCode.OK, "获取列表成功！", OrderDetailPageInfo);
        } catch (Exception e) {
            System.out.println(e);
            return new ResultVO(StatusCode.NO, "获取列表失败！", null);
        }
    }

    @Override
    public ResultVO addOrderDetail(OrderDetail orderDetail) {
        int insert = orderDetailMapper.insert(orderDetail);
        if (insert == 1) {
            Product productBefore = productMapper.selectById(orderDetail.getProductId());

            //扣减库存
            Product product = new Product();
            product.setProductId(productBefore.getProductId());
            product.setProductStock(productBefore.getProductStock() - orderDetail.getBuyNumber());
            productMapper.updateById(product);

            return new ResultVO(StatusCode.OK, "添加成功！", null);
        } else {
            return new ResultVO(StatusCode.NO, "添加失败！", null);
        }
    }
}
