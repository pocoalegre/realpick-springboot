package com.realpick.dao;

import com.realpick.entity.ProductVO;
import com.realpick.entity.ShoppingCart;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.realpick.entity.ShoppingCartVO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 购物车 Mapper 接口
 * </p>
 *
 * @author pocoalegre
 * @since 2021-10-22
 */

@Repository
public interface ShoppingCartMapper extends BaseMapper<ShoppingCart> {

    //根据用户查询购物车VO类
    List<ShoppingCartVO> shoppingCartVO(Integer userId);
}
