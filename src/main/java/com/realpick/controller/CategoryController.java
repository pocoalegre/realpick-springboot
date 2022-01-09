package com.realpick.controller;


import com.alibaba.fastjson.JSON;
import com.realpick.entity.Category;
import com.realpick.service.CategoryService;
import com.realpick.utils.FileManage;
import com.realpick.vo.ResultVO;
import com.realpick.vo.StatusCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 商品类型 前端控制器
 * </p>
 *
 * @author pocoalegre
 * @since 2021-10-22
 */

@RestController
@RequestMapping("/category")
@Api(value = "商品类型操作接口", tags = "商品类型管理")
@CrossOrigin
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @ApiOperation("添加接口")
    @PostMapping("add")
    public ResultVO add(MultipartFile file, String category) {

        //json对象转换
        Category categoryByJson = JSON.parseObject(category, Category.class);
        if (file == null) {
            if (categoryByJson.getCategoryLevel() == 1) {
                return new ResultVO(StatusCode.NO, "必须上传图片！", null);
            } else {
                return categoryService.addCategory(categoryByJson);
            }
        } else {

            //上传文件
            ResultVO saveResultVO = FileManage.fileUpload(file, "static/uploadImg/category");
            if (saveResultVO.getCode() == 10000) {
                categoryByJson.setImgUrl((String) saveResultVO.getData());

                //添加商品类型
                return categoryService.addCategory(categoryByJson);
            } else {
                return saveResultVO;
            }
        }
    }

    @ApiOperation("修改接口")
    @PutMapping("modify")
    public ResultVO modify(MultipartFile file, String category) {

        //json对象转换
        Category categoryByJson = JSON.parseObject(category, Category.class);
        if (file == null) {
            return categoryService.modifyCategory(categoryByJson);
        } else {
            //上传文件
            ResultVO saveResultVO = FileManage.fileUpload(file, "static/uploadImg/category");
            categoryByJson.setImgUrl((String) saveResultVO.getData());
            if (saveResultVO.getCode() == 10000) {
                categoryByJson.setImgUrl((String) saveResultVO.getData());

                //修改商品类型
                return categoryService.modifyCategory(categoryByJson);
            } else {
                return saveResultVO;
            }
        }
    }

    @ApiOperation("条件查询分页列表接口")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "string", name = "queryLevel", value = "商品类型等级"),
            @ApiImplicitParam(dataType = "string", name = "queryType", value = "查询类型"),
            @ApiImplicitParam(dataType = "string", name = "queryInfo", value = "查询内容"),
            @ApiImplicitParam(dataType = "int", name = "pageNum", value = "页码", required = true),
            @ApiImplicitParam(dataType = "int", name = "pageSize", value = "当前页码数据条数", required = true)
    })
    @GetMapping("/list")
    public ResultVO list(@RequestParam("queryLevel") String queryLevel,
                         @RequestParam("queryType") String queryType,
                         @RequestParam("queryInfo") String queryInfo,
                         @RequestParam("pageNum") Integer pageNum,
                         @RequestParam("pageSize") Integer pageSize) {
        return categoryService.categoryList(queryLevel, queryType, queryInfo, pageNum, pageSize);
    }

    @ApiOperation("删除接口")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "int", name = "id", value = "商品类型id", required = true)
    })
    @DeleteMapping("/delete")
    public ResultVO delete(@RequestParam("id") Integer id) {
        return categoryService.deleteCategory(id);
    }

    @ApiOperation("单条接口")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "int", name = "id", value = "商品类型id", required = true)
    })
    @GetMapping("/byId")
    public ResultVO byId(Integer id) {
        return categoryService.categoryById(id);
    }

    @ApiOperation("首页展示列表接口")
    @GetMapping("/indexList")
    public ResultVO indexList() {
        return categoryService.categoryIndexList();
    }
}

