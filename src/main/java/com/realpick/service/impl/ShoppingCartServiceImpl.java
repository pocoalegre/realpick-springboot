package com.realpick.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.realpick.dao.ShoppingCartMapper;
import com.realpick.entity.ShoppingCart;
import com.realpick.entity.ShoppingCartVO;
import com.realpick.service.ShoppingCartService;
import com.realpick.vo.ResultVO;
import com.realpick.vo.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 购物车 服务实现类
 * </p>
 *
 * @author pocoalegre
 * @since 2021-10-22
 */
@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartService {

    @Autowired
    private ShoppingCartMapper shoppingCartMapper;

    @Override
    public ResultVO addShoppingCart(ShoppingCart shoppingCart) {

        //查询条件
        Integer userId = shoppingCart.getUserId();
        Integer productIdByUser = shoppingCart.getProductId();
        HashMap<String, Object> columnMap = new HashMap<>();
        columnMap.put("user_id", userId);
        columnMap.put("product_id", productIdByUser);
        List<ShoppingCart> shoppingCartList = shoppingCartMapper.selectByMap(columnMap);

        //判断该商品在该用户购物车中中是否存在
        if (shoppingCartList.size() != 0) {
            Integer cartNumber = shoppingCart.getCartNumber();
            Integer cartNumberBefore = shoppingCartList.get(0).getCartNumber();

            //修改购物车
            ShoppingCart shoppingCartUpdate = new ShoppingCart();
            shoppingCartUpdate.setCartId(shoppingCartList.get(0).getCartId());
            shoppingCartUpdate.setCartNumber(cartNumberBefore + cartNumber);
            int update = shoppingCartMapper.updateById(shoppingCartUpdate);
            if (update == 1) {
                return new ResultVO(StatusCode.OK, "添加成功！", null);
            } else {
                return new ResultVO(StatusCode.NO, "添加失败！", null);
            }
        } else {
            int insert = shoppingCartMapper.insert(shoppingCart);
            if (insert == 1) {
                return new ResultVO(StatusCode.OK, "添加成功！", null);
            } else {
                return new ResultVO(StatusCode.NO, "添加失败！", null);
            }
        }
    }

    @Override
    public ResultVO modifyShoppingCart(Integer id, Integer number) {

        //修改购物车
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setCartId(id);
        shoppingCart.setCartNumber(number);
        int update = shoppingCartMapper.updateById(shoppingCart);
        if (update == 1) {
            return new ResultVO(StatusCode.OK, "修改成功！", null);
        } else {
            return new ResultVO(StatusCode.NO, "修改失败！", null);
        }
    }

    @Override
    public ResultVO deleteShoppingCart(Integer id) {

        //删除购物车
        int delete = shoppingCartMapper.deleteById(id);
        if (delete == 1) {
            return new ResultVO(StatusCode.OK, "删除成功！", null);
        } else {
            return new ResultVO(StatusCode.NO, "删除失败！", null);
        }
    }

    @Override
    public ResultVO ShoppingCartVOList(Integer pageNum, Integer pageSize, Integer userId) {

        //分页设置
        PageHelper.startPage(pageNum, pageSize);

        try {
            //查询列表并分页
            List<ShoppingCartVO> shoppingCartVOList = shoppingCartMapper.shoppingCartVO(userId);
            PageInfo<ShoppingCartVO> shoppingCartVOPageInfo = new PageInfo<>(shoppingCartVOList);
            return new ResultVO(StatusCode.OK, "获取信息成功！", shoppingCartVOPageInfo);
        } catch (Exception e) {
            System.out.println(e);
            return new ResultVO(StatusCode.NO, "获取信息失败！", null);
        }
    }

    @Override
    public ResultVO deleteAllShoppingCart(Integer id) {

        //查询当前用户所有购物车
        HashMap<String, Object> columnMap = new HashMap<>();
        columnMap.put("user_id", id);

        //清空购物车
        List<ShoppingCart> shoppingCartList = shoppingCartMapper.selectByMap(columnMap);
        for (ShoppingCart shoppingCart : shoppingCartList) {
            int delete = shoppingCartMapper.deleteById(shoppingCart.getCartId());
            if (delete != 1) {
                return new ResultVO(StatusCode.NO, "购物车未知错误！", null);
            }
        }
        return new ResultVO(StatusCode.OK, "清空成功！", null);
    }
}
