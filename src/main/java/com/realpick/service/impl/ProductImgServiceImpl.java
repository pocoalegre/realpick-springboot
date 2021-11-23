package com.realpick.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.realpick.dao.ProductImgMapper;
import com.realpick.dao.ProductMapper;
import com.realpick.entity.Product;
import com.realpick.entity.ProductImg;
import com.realpick.service.ProductImgService;
import com.realpick.vo.ResultVO;
import com.realpick.vo.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 商品图片 服务实现类
 * </p>
 *
 * @author pocoalegre
 * @since 2021-10-22
 */
@Service
public class ProductImgServiceImpl extends ServiceImpl<ProductImgMapper, ProductImg> implements ProductImgService {

    @Autowired
    private ProductImgMapper productImgMapper;

    @Autowired
    private ProductMapper productMapper;

    @Override
    public ResultVO productImgList(String queryProductId, Integer pageNum, Integer pageSize) {

        //分页设置
        PageHelper.startPage(pageNum, pageSize);

        //查询条件封装
        QueryWrapper<ProductImg> qw = new QueryWrapper<>();

        //判断是否有查询条件
        if (!queryProductId.equals("")){
            qw.eq("product_id", Integer.valueOf(queryProductId));
            qw.orderByAsc("img_seq");
        }

        //查询列表并分页
        try {
            List<ProductImg> productImgList = productImgMapper.selectList(qw);
            PageInfo<ProductImg> productImgPageInfo = new PageInfo<>(productImgList);
            return new ResultVO(StatusCode.OK, "获取列表成功！", productImgPageInfo);
        }catch (Exception e){
            System.out.println(e);
            return new ResultVO(StatusCode.NO, "获取列表失败！", null);
        }
    }

    @Override
    public ResultVO productImgById(Integer id) {
        try {
            ProductImg productImg = productImgMapper.selectById(id);
            return new ResultVO(StatusCode.OK, "获取信息成功！", productImg);
        }catch (Exception e){
            System.out.println(e);
            return new ResultVO(StatusCode.NO, "获取信息失败！", null);
        }
    }

    @Override
    public ResultVO deleteProductImg(Integer id) {
        int delete = productImgMapper.deleteById(id);
        if (delete == 1){
            return new ResultVO(StatusCode.OK, "删除成功！", null);
        }else {
            return new ResultVO(StatusCode.NO, "删除失败！", null);
        }
    }

    @Override
    public ResultVO modifyProductImg(ProductImg productImg) {

        //查询是否有该商品id
        Integer productId = productImg.getProductId();
        HashMap<String, Object> columnMap1 = new HashMap<>();
        columnMap1.put("product_id", productId);
        List<Product> productList1 = productMapper.selectByMap(columnMap1);
        if (productList1.size() == 0){
            return new ResultVO(StatusCode.NO, "该商品编号不存在！", null);
        }else {

            //判断图片顺序是否重复
            Integer imgSeq = productImg.getImgSeq();
            HashMap<String, Object> columnMap2 = new HashMap<>();
            columnMap2.put("product_id", productId);
            columnMap2.put("img_seq", imgSeq);
            List<ProductImg> productImgList2 = productImgMapper.selectByMap(columnMap2);
            if (productImgList2.size() == 1){
                if (!productImg.getImgId().equals(productImgList2.get(0).getImgId())){
                    return new ResultVO(StatusCode.NO, "图片顺序重复！", null);
                }
            }else if (productImgList2.size() > 1){
                return new ResultVO(StatusCode.NO, "图片顺序异常！", null);
            }

            //判断主图是否重复
            Integer isMain = productImg.getIsMain();
            if (isMain == 1){
                HashMap<String, Object> columnMap3 = new HashMap<>();
                columnMap3.put("product_id", productId);
                columnMap3.put("is_main", isMain);
                List<ProductImg> productImgList3 = productImgMapper.selectByMap(columnMap3);
                if (productImgList3.size() == 1){
                    if (!productImg.getImgId().equals(productImgList3.get(0).getImgId())){
                        return new ResultVO(StatusCode.NO, "主图已存在！", null);
                    }
                }else if (productImgList3.size() > 1){
                    return new ResultVO(StatusCode.NO, "主图异常！", null);
                }
            }

            //修改
            int update = productImgMapper.updateById(productImg);
            if (update == 1){
                return new ResultVO(StatusCode.OK, "修改成功！", null);
            }else {
                return new ResultVO(StatusCode.NO, "修改失败！", null);
            }
        }
    }

    @Override
    public ResultVO addProductImg(ProductImg productImg) {

        //查询是否有该商品id
        Integer productId = productImg.getProductId();
        HashMap<String, Object> columnMap = new HashMap<>();
        columnMap.put("product_id", productId);
        List<Product> productList = productMapper.selectByMap(columnMap);
        if (productList.size() == 0){
            return new ResultVO(StatusCode.NO, "该商品编号不存在！", null);
        }else {

            //判断图片顺序是否重复
            Integer imgSeq = productImg.getImgSeq();
            HashMap<String, Object> columnMap2 = new HashMap<>();
            columnMap2.put("product_id", productId);
            columnMap2.put("img_seq", imgSeq);
            List<ProductImg> productImgList2 = productImgMapper.selectByMap(columnMap2);
            if (productImgList2.size() != 0){
                return new ResultVO(StatusCode.NO, "图片顺序重复！", null);
            }

            //判断主图是否重复
            Integer isMain = productImg.getIsMain();
            if (isMain == 1){
                HashMap<String, Object> columnMap3 = new HashMap<>();
                columnMap3.put("product_id", productId);
                columnMap3.put("is_main", isMain);
                List<ProductImg> productImgList3 = productImgMapper.selectByMap(columnMap3);
                if (productImgList3.size() != 0){
                    return new ResultVO(StatusCode.NO, "主图已存在！", null);
                }
            }

            //修改
            int insert = productImgMapper.insert(productImg);
            if (insert == 1){
                return new ResultVO(StatusCode.OK, "添加成功！", null);
            }else {
                return new ResultVO(StatusCode.NO, "添加失败！", null);
            }
        }
    }
}
