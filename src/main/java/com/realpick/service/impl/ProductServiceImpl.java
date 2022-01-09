package com.realpick.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.realpick.dao.*;
import com.realpick.entity.Category;
import com.realpick.entity.Product;
import com.realpick.entity.ProductVO;
import com.realpick.service.ProductService;
import com.realpick.vo.ResultVO;
import com.realpick.vo.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 商品 服务实现类
 * </p>
 *
 * @author pocoalegre
 * @since 2021-10-22
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductParamMapper productParamMapper;

    @Autowired
    private ProductSkuMapper productSkuMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public ResultVO productList(String queryType, String queryInfo, Integer pageNum, Integer pageSize) {

        //分页设置
        PageHelper.startPage(pageNum, pageSize);

        //查询条件封装
        QueryWrapper<Product> qw = new QueryWrapper<>();

        //判断是否有查询条件
        if (!queryType.equals("")) {
            if (queryType.equals("category_id")) {
                if (!queryInfo.equals("")) {
                    try {
                        qw.eq(queryType, Integer.valueOf(queryInfo));
                    } catch (Exception e) {
                        System.out.println(e);
                        return new ResultVO(StatusCode.NO, "请输入合法的编号！", null);
                    }
                }
            } else {
                if (!queryInfo.equals("")) {
                    qw.like(queryType, queryInfo);
                }
            }
        }

        //查询列表并分页
        try {
            List<Product> productList = productMapper.selectList(qw);
            PageInfo<Product> productPageInfo = new PageInfo<>(productList);
            return new ResultVO(StatusCode.OK, "获取列表成功！", productPageInfo);
        } catch (Exception e) {
            System.out.println(e);
            return new ResultVO(StatusCode.NO, "获取列表失败！", null);
        }
    }

    @Override
    public ResultVO productById(Integer id) {
        try {
            Product product = productMapper.selectById(id);
            return new ResultVO(StatusCode.OK, "获取信息成功！", product);
        } catch (Exception e) {
            System.out.println(e);
            return new ResultVO(StatusCode.NO, "获取信息失败！", null);
        }
    }

    @Override
    public ResultVO deleteProduct(Integer id) {

        HashMap<String, Object> columnMap = new HashMap<>();
        columnMap.put("product_id", id);

        //删除商品相关信息
        try {
            productParamMapper.deleteByMap(columnMap);
            productSkuMapper.deleteByMap(columnMap);
            int delete = productMapper.deleteById(id);
            if (delete == 1) {
                return new ResultVO(StatusCode.OK, "删除成功！", null);
            } else {
                return new ResultVO(StatusCode.NO, "删除失败！", null);
            }
        } catch (Exception e) {
            System.out.println(e);
            return new ResultVO(StatusCode.NO, "删除异常！", null);
        }
    }

    @Override
    public ResultVO modifyProduct(Product product) {

        //查询是否有该类型
        Integer categoryId = product.getCategoryId();
        HashMap<String, Object> columnMap = new HashMap<>();
        columnMap.put("category_id", categoryId);
        List<Category> categoryList = categoryMapper.selectByMap(columnMap);
        if (categoryList.size() == 0) {
            return new ResultVO(StatusCode.NO, "该商品类型不存在！", null);
        } else {
            int update = productMapper.updateById(product);
            if (update == 1) {
                return new ResultVO(StatusCode.OK, "修改成功！", null);
            } else {
                return new ResultVO(StatusCode.NO, "修改失败！", null);
            }
        }
    }

    @Override
    public ResultVO addProduct(Product product) {

        //查询是否有该类型
        Integer categoryId = product.getCategoryId();

        HashMap<String, Object> columnMap = new HashMap<>();
        columnMap.put("category_id", categoryId);
        List<Category> categoryList = categoryMapper.selectByMap(columnMap);
        if (categoryList.size() == 0) {
            return new ResultVO(StatusCode.NO, "该商品类型不存在！", null);
        } else {
            product.setProductSales(0);
            int insert = productMapper.insert(product);
            if (insert == 1) {
                return new ResultVO(StatusCode.OK, "添加成功！", null);
            } else {
                return new ResultVO(StatusCode.NO, "添加失败！", null);
            }
        }
    }

    @Override
    public ResultVO productIndexList() {

        //查询列表
        try {
            //前10个
            QueryWrapper<Product> qw = new QueryWrapper<>();
            qw.last("limit 10");

            List<Product> productList = productMapper.selectList(qw);
            return new ResultVO(StatusCode.OK, "获取列表成功！", productList);
        } catch (Exception e) {
            System.out.println(e);
            return new ResultVO(StatusCode.NO, "获取列表失败！", null);
        }
    }

    @Override
    public ResultVO productListByCategory(Integer pageNum, Integer pageSize) {

        //分页设置
        PageHelper.startPage(pageNum, pageSize);

        //查询列表并分页
        try {
            List<Product> productList = productMapper.selectList(null);
            PageInfo<Product> productPageInfo = new PageInfo<>(productList);
            return new ResultVO(StatusCode.OK, "获取列表成功！", productPageInfo);
        } catch (Exception e) {
            System.out.println(e);
            return new ResultVO(StatusCode.NO, "获取列表失败！", null);
        }
    }

    @Override
    public ResultVO productVOById(Integer id) {
        try {
            ProductVO productVO = productMapper.productVO(id);
            return new ResultVO(StatusCode.OK, "获取信息成功！", productVO);
        } catch (Exception e) {
            System.out.println(e);
            return new ResultVO(StatusCode.NO, "获取信息失败！", null);
        }
    }
}
