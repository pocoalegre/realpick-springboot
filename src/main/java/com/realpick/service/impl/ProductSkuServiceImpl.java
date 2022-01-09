package com.realpick.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.realpick.dao.ProductMapper;
import com.realpick.entity.Product;
import com.realpick.entity.ProductSku;
import com.realpick.dao.ProductSkuMapper;
import com.realpick.service.ProductSkuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.realpick.vo.ResultVO;
import com.realpick.vo.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * sku 服务实现类
 * </p>
 *
 * @author pocoalegre
 * @since 2021-10-22
 */
@Service
public class ProductSkuServiceImpl extends ServiceImpl<ProductSkuMapper, ProductSku> implements ProductSkuService {

    @Autowired
    private ProductSkuMapper productSkuMapper;

    @Autowired
    private ProductMapper productMapper;

    @Override
    public ResultVO productSkuList(String queryProductId, Integer pageNum, Integer pageSize) {

        //分页设置
        PageHelper.startPage(pageNum, pageSize);

        //查询条件封装
        QueryWrapper<ProductSku> qw = new QueryWrapper<>();

        //判断是否有查询条件
        if (!queryProductId.equals("")) {
            qw.eq("product_id", Integer.valueOf(queryProductId));
        }

        //查询列表并分页
        try {
            List<ProductSku> productSkuList = productSkuMapper.selectList(qw);
            PageInfo<ProductSku> productSkuPageInfo = new PageInfo<>(productSkuList);
            return new ResultVO(StatusCode.OK, "获取列表成功！", productSkuPageInfo);
        } catch (Exception e) {
            System.out.println(e);
            return new ResultVO(StatusCode.NO, "获取列表失败！", null);
        }
    }

    @Override
    public ResultVO productSkuById(Integer id) {
        try {
            ProductSku productSku = productSkuMapper.selectById(id);
            return new ResultVO(StatusCode.OK, "获取信息成功！", productSku);
        } catch (Exception e) {
            System.out.println(e);
            return new ResultVO(StatusCode.NO, "获取信息失败！", null);
        }
    }

    @Override
    public ResultVO deleteProductSku(Integer id) {
        int delete = productSkuMapper.deleteById(id);
        if (delete == 1) {
            return new ResultVO(StatusCode.OK, "删除成功！", null);
        } else {
            return new ResultVO(StatusCode.NO, "删除失败！", null);
        }
    }

    @Override
    public ResultVO modifyProductSku(ProductSku productSku) {

        //查询是否有该商品id
        Integer productId = productSku.getProductId();
        HashMap<String, Object> columnMap = new HashMap<>();
        columnMap.put("product_id", productId);
        List<Product> productList = productMapper.selectByMap(columnMap);
        if (productList.size() == 0) {
            return new ResultVO(StatusCode.NO, "该商品编号不存在！", null);
        } else {
            int update = productSkuMapper.updateById(productSku);
            if (update == 1) {
                return new ResultVO(StatusCode.OK, "修改成功！", null);
            } else {
                return new ResultVO(StatusCode.NO, "修改失败！", null);
            }
        }
    }

    @Override
    public ResultVO addProductSku(ProductSku productSku) {

        //查询是否有该商品id
        Integer productId = productSku.getProductId();
        HashMap<String, Object> columnMap = new HashMap<>();
        columnMap.put("product_id", productId);
        List<Product> productList = productMapper.selectByMap(columnMap);
        if (productList.size() == 0) {
            return new ResultVO(StatusCode.NO, "该商品编号不存在！", null);
        } else {
            int insert = productSkuMapper.insert(productSku);
            if (insert == 1) {
                return new ResultVO(StatusCode.OK, "添加成功！", null);
            } else {
                return new ResultVO(StatusCode.NO, "添加失败！", null);
            }
        }
    }

    @Override
    public ResultVO productSkuByProductId(Integer id) {

        //查询单条商品sku信息
        HashMap<String, Object> columnMap = new HashMap<>();
        columnMap.put("product_id", id);
        try {
            List<ProductSku> productSkuList = productSkuMapper.selectByMap(columnMap);
            return new ResultVO(StatusCode.OK, "获取列表成功！", productSkuList);
        } catch (Exception e) {
            System.out.println(e);
            return new ResultVO(StatusCode.NO, "获取信息失败！", null);
        }
    }
}
