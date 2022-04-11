package com.realpick.controller;


import com.alibaba.fastjson.JSON;
import com.realpick.entity.Product;
import com.realpick.service.ProductService;
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
 * 商品 前端控制器
 * </p>
 *
 * @author pocoalegre
 * @since 2021-10-22
 */

@RestController
@RequestMapping("/product")
@Api(value = "商品操作接口", tags = "商品管理")
@CrossOrigin
public class ProductController {

    @Autowired
    private ProductService productService;

    @ApiOperation("条件查询分页列表接口")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "string", name = "queryType", value = "查询类型"),
            @ApiImplicitParam(dataType = "string", name = "queryInfo", value = "查询内容"),
            @ApiImplicitParam(dataType = "int", name = "pageNum", value = "页码", required = true),
            @ApiImplicitParam(dataType = "int", name = "pageSize", value = "当前页码数据条数", required = true)
    })
    @GetMapping("/list")
    public ResultVO list(@RequestParam("queryType") String queryType,
                         @RequestParam("queryInfo") String queryInfo,
                         @RequestParam("pageNum") Integer pageNum,
                         @RequestParam("pageSize") Integer pageSize) {
        return productService.productList(queryType, queryInfo, pageNum, pageSize);
    }

    @ApiOperation("单条接口")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "int", name = "id", value = "商品id", required = true)
    })
    @GetMapping("/byId")
    public ResultVO byId(@RequestParam("id") Integer id) {
        return productService.productById(id);
    }

    @ApiOperation("删除接口")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "int", name = "id", value = "商品id", required = true)
    })
    @DeleteMapping("/delete")
    public ResultVO delete(@RequestParam("id") Integer id) {
        return productService.deleteProduct(id);
    }

    @ApiOperation("修改接口")
    @PutMapping("/modify")
    public ResultVO modify(MultipartFile file, String product) {

        //json对象转换
        Product productByJson = JSON.parseObject(product, Product.class);

        //非空字段
        if (file == null) {
            return productService.modifyProduct(productByJson);
        } else {

            //上传文件
            ResultVO saveResultVO = FileManage.fileUpload(file, "static/uploadImg/product/img");
            if (saveResultVO.getCode() == 10000) {
                productByJson.setProductImg((String) saveResultVO.getData());

                //添加商品图片
                return productService.modifyProduct(productByJson);
            } else {
                return saveResultVO;
            }
        }
    }

    @ApiOperation("添加接口")
    @PostMapping("/add")
    public ResultVO add(MultipartFile file, String product) {

        //json对象转换
        Product productByJson = JSON.parseObject(product, Product.class);

        //非空字段
        if (file == null) {
            return new ResultVO(StatusCode.NO, "必须上传图片！", null);
        } else {

            //上传文件
            ResultVO saveResultVO = FileManage.fileUpload(file, "static/uploadImg/product/img");
            if (saveResultVO.getCode() == 10000) {
                productByJson.setProductImg((String) saveResultVO.getData());

                //添加商品图片
                return productService.addProduct(productByJson);
            } else {
                return saveResultVO;
            }
        }
    }

    @ApiOperation("首页列表接口")
    @GetMapping("/indexList")
    public ResultVO indexList() {
        return productService.productIndexList();
    }

    @ApiOperation("分类列表接口")
    @GetMapping("/listByCategory")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "int", name = "pageNum", value = "页码", required = true),
            @ApiImplicitParam(dataType = "int", name = "pageSize", value = "当前页码数据条数", required = true),
            @ApiImplicitParam(dataType = "int", name = "categoryId", value = "商品类型id", required = true)
    })
    public ResultVO listByCategory(@RequestParam("pageNum") Integer pageNum,
                                   @RequestParam("pageSize") Integer pageSize,
                                   @RequestParam("categoryId") Integer categoryId) {
        return productService.productListByCategory(pageNum, pageSize, categoryId);
    }

    @ApiOperation("商品VO接口")
    @GetMapping("/voById")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "int", name = "id", value = "商品id", required = true)})
    public ResultVO voById(Integer id) {
        return productService.productVOById(id);
    }
}

