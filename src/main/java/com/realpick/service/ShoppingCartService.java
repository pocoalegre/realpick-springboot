package com.realpick.service;

import com.realpick.entity.ShoppingCart;
import com.baomidou.mybatisplus.extension.service.IService;
import com.realpick.vo.ResultVO;
import org.apache.ibatis.io.ResolverUtil;

import java.util.List;

/**
 * <p>
 * 购物车 服务类
 * </p>
 *
 * @author pocoalegre
 * @since 2021-10-22
 */
public interface ShoppingCartService extends IService<ShoppingCart> {

    //添加购物车
    ResultVO addShoppingCart(ShoppingCart shoppingCart);

    //修改购物车
    ResultVO modifyShoppingCart(Integer id, Integer number);

    //删除购物车
    ResultVO deleteShoppingCart(Integer id);

    //购物车查询
    ResultVO ShoppingCartVOList(Integer pageNum, Integer pageSize, Integer userId);

    //删除购物车
    ResultVO deleteShoppingCartByUser(List<Integer> idList);

}
