package com.realpick.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.realpick.dao.ProductMapper;
import com.realpick.dao.ProductParamMapper;
import com.realpick.entity.Product;
import com.realpick.entity.ProductParam;
import com.realpick.entity.ProductSku;
import com.realpick.service.ProductParamService;
import com.realpick.vo.ResultVO;
import com.realpick.vo.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 商品参数 服务实现类
 * </p>
 *
 * @author pocoalegre
 * @since 2021-10-22
 */
@Service
public class ProductParamServiceImpl extends ServiceImpl<ProductParamMapper, ProductParam> implements ProductParamService {

    @Autowired
    private ProductParamMapper productParamMapper;

    @Autowired
    private ProductMapper productMapper;

    @Override
    public ResultVO productParamList(String queryProductId, Integer pageNum, Integer pageSize) {

        //分页设置
        PageHelper.startPage(pageNum, pageSize);

        //查询条件封装
        QueryWrapper<ProductParam> qw = new QueryWrapper<>();

        //判断是否有查询条件
        if (!queryProductId.equals("")) {
            qw.eq("product_id", Integer.valueOf(queryProductId));
        }

        //查询列表并分页
        try {
            List<ProductParam> productParamList = productParamMapper.selectList(qw);
            PageInfo<ProductParam> productParamPageInfo = new PageInfo<>(productParamList);
            return new ResultVO(StatusCode.OK, "获取列表成功！", productParamPageInfo);
        } catch (Exception e) {
            System.out.println(e);
            return new ResultVO(StatusCode.NO, "获取列表失败！", null);
        }
    }

    @Override
    public ResultVO productParamById(Integer id) {
        try {
            ProductParam productParam = productParamMapper.selectById(id);
            return new ResultVO(StatusCode.OK, "获取信息成功！", productParam);
        } catch (Exception e) {
            System.out.println(e);
            return new ResultVO(StatusCode.NO, "获取信息失败！", null);
        }
    }

    @Override
    public ResultVO deleteProductParam(Integer id) {
        int delete = productParamMapper.deleteById(id);
        if (delete == 1) {
            return new ResultVO(StatusCode.OK, "删除成功！", null);
        } else {
            return new ResultVO(StatusCode.NO, "删除失败！", null);
        }
    }

    @Override
    public ResultVO modifyProductParam(ProductParam productParam) {

        //查询是否有该商品id
        Integer productId = productParam.getProductId();
        HashMap<String, Object> columnMap = new HashMap<>();
        columnMap.put("product_id", productId);
        List<Product> productList = productMapper.selectByMap(columnMap);
        if (productList.size() == 0) {
            return new ResultVO(StatusCode.NO, "该商品编号不存在！", null);
        } else {
            int update = productParamMapper.updateById(productParam);
            if (update == 1) {
                return new ResultVO(StatusCode.OK, "修改成功！", null);
            } else {
                return new ResultVO(StatusCode.NO, "修改失败！", null);
            }
        }
    }

    @Override
    public ResultVO addProductParam(ProductParam productParam) {

        //查询是否有该商品id
        Integer productId = productParam.getProductId();
        HashMap<String, Object> columnMap = new HashMap<>();
        columnMap.put("product_id", productId);
        List<Product> productList = productMapper.selectByMap(columnMap);
        if (productList.size() == 0) {
            return new ResultVO(StatusCode.NO, "该商品编号不存在！", null);
        } else {
            int insert = productParamMapper.insert(productParam);
            if (insert == 1) {
                return new ResultVO(StatusCode.OK, "添加成功！", null);
            } else {
                return new ResultVO(StatusCode.NO, "添加失败！", null);
            }
        }
    }

    @Override
    public ResultVO productParamByProductId(Integer id) {

        //查询单条商品参数信息
        HashMap<String, Object> columnMap = new HashMap<>();
        columnMap.put("product_id", id);
        try {
            List<ProductParam> productParamList = productParamMapper.selectByMap(columnMap);
            return new ResultVO(StatusCode.OK, "获取列表成功！", productParamList);
        } catch (Exception e) {
            System.out.println(e);
            return new ResultVO(StatusCode.NO, "获取信息失败！", null);
        }
    }
}
