package com.realpick.dao;

import com.realpick.entity.Product;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 商品 Mapper 接口
 * </p>
 *
 * @author pocoalegre
 * @since 2021-10-22
 */

@Repository
public interface ProductMapper extends BaseMapper<Product> {

}
