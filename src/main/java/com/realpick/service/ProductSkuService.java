package com.realpick.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.realpick.entity.ProductSku;
import com.realpick.vo.ResultVO;

/**
 * <p>
 * sku 服务类
 * </p>
 *
 * @author pocoalegre
 * @since 2021-10-22
 */
public interface ProductSkuService extends IService<ProductSku> {

    //商品sku列表
    ResultVO productSkuList(String queryProductId, Integer pageNum, Integer pageSize);

    //单条查询
    ResultVO productSkuById(Integer id);

    //删除商品sku
    ResultVO deleteProductSku(Integer id);

    //修改商品sku
    ResultVO modifyProductSku(ProductSku productSku);

    //添加商品sku
    ResultVO addProductSku(ProductSku productSku);

    //查询单件商品sku
    ResultVO productSkuByProductId(Integer id);

    //获取sku尺寸列表
    ResultVO productSkuSizeList(Integer id);

    //获取sku颜色列表
    ResultVO productSkuColorList(Integer id);

    //二次获取sku颜色列表
    ResultVO reProductSkuColorList(Integer id, String skuSize);

    //二次获取sku尺寸列表
    ResultVO reProductSkuSizeList(Integer id, String skuColor);

    //根据商品id、sku尺寸和颜色查询id
    ResultVO selectIdByThree(Integer productId, String skuSize, String skuColor);

}
