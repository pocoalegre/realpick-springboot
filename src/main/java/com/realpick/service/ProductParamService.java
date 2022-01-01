package com.realpick.service;

import com.realpick.entity.ProductParam;
import com.baomidou.mybatisplus.extension.service.IService;
import com.realpick.vo.ResultVO;

/**
 * <p>
 * 商品参数 服务类
 * </p>
 *
 * @author pocoalegre
 * @since 2021-10-22
 */
public interface ProductParamService extends IService<ProductParam> {

    //商品参数列表
    ResultVO productParamList(String queryProductId, Integer pageNum, Integer pageSize);

    //单条查询
    ResultVO productParamById(Integer id);

    //删除商品参数
    ResultVO deleteProductParam(Integer id);

    //修改商品参数
    ResultVO modifyProductParam(ProductParam productParam);

    //添加商品参数
    ResultVO addProductParam(ProductParam productParam);

    //查询单件商品参数
    ResultVO productParamByProductId(Integer id);
}
