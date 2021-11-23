package com.realpick.service;

import com.realpick.entity.ProductImg;
import com.baomidou.mybatisplus.extension.service.IService;
import com.realpick.vo.ResultVO;

/**
 * <p>
 * 商品图片 服务类
 * </p>
 *
 * @author pocoalegre
 * @since 2021-10-22
 */
public interface ProductImgService extends IService<ProductImg> {

    //商品图片列表
    ResultVO productImgList(String queryProductId, Integer pageNum, Integer pageSize);

    //单条查询
    ResultVO productImgById(Integer id);

    //删除商品图片
    ResultVO deleteProductImg(Integer id);

    //修改商品图片
    ResultVO modifyProductImg(ProductImg productImg);

    //添加商品图片
    ResultVO addProductImg(ProductImg productImg);
}
