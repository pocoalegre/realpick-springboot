package com.realpick.dao;

import com.realpick.entity.ProductSku;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * sku Mapper 接口
 * </p>
 *
 * @author pocoalegre
 * @since 2021-10-22
 */

@Repository
public interface ProductSkuMapper extends BaseMapper<ProductSku> {

    //查询skuSize列表
    List<String> skuSizeList(Integer productId);

    //查询skuColor列表
    List<String> skuColorList(Integer productId);

    //二次查询skuColor列表
    List<String> reSkuColorList(Integer productId, String skuSize);

    //二次查询skuSize列表
    List<String> reSkuSizeList(Integer productId, String skuColor);

}
