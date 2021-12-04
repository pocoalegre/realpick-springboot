package com.realpick.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.realpick.dao.CategoryMapper;
import com.realpick.entity.Category;
import com.realpick.service.CategoryService;
import com.realpick.vo.ResultVO;
import com.realpick.vo.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 商品类型 服务实现类
 * </p>
 *
 * @author pocoalegre
 * @since 2021-10-22
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public ResultVO addCategory(Category category) {
        if (category.getParentId() == null){
            int insert = categoryMapper.insert(category);
            if (insert == 1){
                return new ResultVO(StatusCode.OK, "添加成功！", null);
            }else {
                return new ResultVO(StatusCode.NO, "添加失败！", null);
            }
        }else {

            //查询是否有该父类型
            HashMap<String, Object> columnMap = new HashMap<>();
            columnMap.put("category_id", category.getParentId());
            List<Category> categoryList = categoryMapper.selectByMap(columnMap);
            if (categoryList.size() == 0){
                return new ResultVO(StatusCode.NO, "不存在该父类型！", null);
            }else {
                int insert = categoryMapper.insert(category);
                if (insert == 1){
                    return new ResultVO(StatusCode.OK, "添加成功！", null);
                }else {
                    return new ResultVO(StatusCode.NO, "添加失败！", null);
                }
            }
        }
    }

    @Override
    public ResultVO modifyCategory(Category category) {
        if (category.getParentId() == null){
            int update = categoryMapper.updateById(category);
            if (update == 1){
                return new ResultVO(StatusCode.OK, "修改成功！", null);
            }else {
                return new ResultVO(StatusCode.NO, "修改失败！", null);
            }
        }else {

            //查询是否有该父类型
            HashMap<String, Object> columnMap = new HashMap<>();
            columnMap.put("category_id", category.getParentId());
            List<Category> categoryList = categoryMapper.selectByMap(columnMap);
            if (categoryList.size() == 0){
                return new ResultVO(StatusCode.NO, "不存在该父类型！", null);
            }else {
                int update = categoryMapper.updateById(category);
                if (update == 1){
                    return new ResultVO(StatusCode.OK, "修改成功！", null);
                }else {
                    return new ResultVO(StatusCode.NO, "修改失败！", null);
                }
            }
        }
    }

    @Override
    public ResultVO categoryList(String queryLevel, String queryType, String queryInfo, Integer pageNum, Integer pageSize) {

        //分页设置
        PageHelper.startPage(pageNum, pageSize);

        //查询条件封装
        QueryWrapper<Category> qw = new QueryWrapper<>();

        //判断是否有查询条件
        if (!queryLevel.equals("")){
            qw.eq("category_level", Integer.valueOf(queryLevel));
        }
        if (!queryType.equals("")){
            if (queryType.equals("parent_id")){
                if (!queryInfo.equals("")){
                    try {
                        qw.eq(queryType, Integer.valueOf(queryInfo));
                    }catch (Exception e){
                        System.out.println(e);
                        return new ResultVO(StatusCode.NO, "请输入合法的编号！", null);
                    }
                }
            }else {
                if (!queryInfo.equals("")){
                    qw.like(queryType, queryInfo);
                }
            }
        }

        //查询列表并分页
        try {
            List<Category> categoryList = categoryMapper.selectList(qw);
            PageInfo<Category> categoryPageInfo = new PageInfo<>(categoryList);
            return new ResultVO(StatusCode.OK, "获取列表成功！", categoryPageInfo);
        }catch (Exception e){
            System.out.println(e);
            return new ResultVO(StatusCode.NO, "获取列表失败！", null);
        }
    }

    @Override
    public ResultVO deleteCategory(Integer id) {
        int delete = categoryMapper.deleteById(id);
        if (delete == 1){
            return new ResultVO(StatusCode.OK, "删除成功！", null);
        }else {
            return new ResultVO(StatusCode.NO, "删除失败！", null);
        }
    }

    @Override
    public ResultVO categoryById(Integer id) {
        try {
            Category category = categoryMapper.selectById(id);
            return new ResultVO(StatusCode.OK, "获取信息成功！", category);
        }catch (Exception e){
            System.out.println(e);
            return new ResultVO(StatusCode.NO, "获取信息失败！", null);
        }
    }

    @Override
    public ResultVO categoryIndexList() {

        //查询列表
        try {
            //一级类型前5个查询
            QueryWrapper<Category> qw = new QueryWrapper<>();
            qw.last("limit 5");

            List<Category> categoryList = categoryMapper.selectList(qw);
            return new ResultVO(StatusCode.OK, "获取列表成功！", categoryList);
        }catch (Exception e){
            System.out.println(e);
            return new ResultVO(StatusCode.NO, "获取列表失败！", null);
        }
    }
}
