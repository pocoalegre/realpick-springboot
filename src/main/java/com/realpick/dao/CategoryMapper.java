package com.realpick.dao;

import com.realpick.entity.Category;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.realpick.entity.CategoryVO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 商品类型 Mapper 接口
 * </p>
 *
 * @author pocoalegre
 * @since 2021-10-22
 */

@Repository
public interface CategoryMapper extends BaseMapper<Category> {

    //查询类型表格
    List<CategoryVO> CategoryVO();

}
