package com.realpick.service;

import com.realpick.entity.Product;
import com.baomidou.mybatisplus.extension.service.IService;
import com.realpick.vo.ResultVO;

/**
 * <p>
 * 商品 服务类
 * </p>
 *
 * @author pocoalegre
 * @since 2021-10-22
 */
public interface ProductService extends IService<Product> {

    //商品列表
    ResultVO productList(String queryType, String queryInfo, Integer pageNum, Integer pageSize);

    //单条查询
    ResultVO productById(Integer id);

    //删除商品
    ResultVO deleteProduct(Integer id);

    //修改商品
    ResultVO modifyProduct(Product product);

    //添加商品
    ResultVO addProduct(Product product);

    //首页商品展示
    ResultVO productIndexList();

    //商品分类展示
    ResultVO productListByCategory(Integer pageNum, Integer pageSize, Integer categoryId);

    //商品VO
    ResultVO productVOById(Integer id);

}
