package com.realpick.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.realpick.dao.OrderDetailMapper;
import com.realpick.dao.OrdersMapper;
import com.realpick.entity.Orders;
import com.realpick.service.OrdersService;
import com.realpick.vo.ResultVO;
import com.realpick.vo.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

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

    @Override
    public ResultVO orderList(String queryType, String queryInfo, Integer pageNum, Integer pageSize) {

        //分页设置
        PageHelper.startPage(pageNum, pageSize);

        //查询条件封装
        QueryWrapper<Orders> qw = new QueryWrapper<>();

        //判断是否有查询条件
        if (!queryType.equals("")){
            if (queryType.equals("user_id")){
                if (!queryInfo.equals("")){
                    try {
                        qw.eq(queryType, Integer.valueOf(queryInfo));
                    }catch (Exception e){
                        System.out.println(e);
                        return new ResultVO(StatusCode.NO, "请输入合法的编号！", null);
                    }
                }
            }else {
                if (!queryInfo.equals("")){
                    qw.like(queryType, queryInfo);
                }
            }
        }

        //查询列表并分页
        try {
            List<Orders> OrderList = ordersMapper.selectList(qw);
            PageInfo<Orders> OrderPageInfo = new PageInfo<>(OrderList);
            return new ResultVO(StatusCode.OK, "获取列表成功！", OrderPageInfo);
        }catch (Exception e){
            System.out.println(e);
            return new ResultVO(StatusCode.NO, "获取列表失败！", null);
        }
    }

    @Override
    public ResultVO orderById(Integer id) {
        try {
            Orders order = ordersMapper.selectById(id);
            return new ResultVO(StatusCode.OK, "获取信息成功！", order);
        }catch (Exception e){
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
            if (delete == 1){
                return new ResultVO(StatusCode.OK, "删除成功！", null);
            }else {
                return new ResultVO(StatusCode.NO, "删除失败！", null);
            }
        }catch (Exception e){
            System.out.println(e);
            return new ResultVO(StatusCode.NO, "删除异常！", null);
        }
    }

    @Override
    public ResultVO modifyOrder(Orders order) {
        int update = ordersMapper.updateById(order);
        if (update == 1){
            return new ResultVO(StatusCode.OK, "修改成功！", null);
        }else {
            return new ResultVO(StatusCode.NO, "修改失败！", null);
        }
    }
}
