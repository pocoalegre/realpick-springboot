package com.realpick.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.realpick.entity.Category;
import com.realpick.vo.ResultVO;

/**
 * <p>
 * 商品类型 服务类
 * </p>
 *
 * @author pocoalegre
 * @since 2021-10-22
 */
public interface CategoryService extends IService<Category> {

    //添加商品类型
    ResultVO addCategory(Category category);

    //修改商品类型
    ResultVO modifyCategory(Category category);

    //商品类型列表
    ResultVO categoryList(String queryLevel, String queryInfo, Integer pageNum, Integer pageSize);

    //删除商品类型
    ResultVO deleteCategory(Integer id);

    //单条查询
    ResultVO categoryById(Integer id);
}
