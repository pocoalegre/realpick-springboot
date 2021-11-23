package com.realpick.controller;


import com.realpick.entity.Product;
import com.realpick.service.ProductService;
import com.realpick.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
                         @RequestParam("pageSize")Integer pageSize) {
        return productService.productList(queryType, queryInfo, pageNum, pageSize);
    }

    @ApiOperation("单条接口")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "int", name = "id", value = "商品id", required = true)
    })
    @GetMapping("/byId")
    public ResultVO byId(@RequestParam("id") Integer id){
        return productService.productById(id);
    }

    @ApiOperation("删除接口")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "int", name = "id", value = "商品id", required = true)
    })
    @DeleteMapping("/delete")
    public ResultVO delete(@RequestParam("id") Integer id){
        return productService.deleteProduct(id);
    }

    @ApiOperation("修改接口")
    @PutMapping("/modify")
    public ResultVO modify(@RequestBody Product product){
        return productService.modifyProduct(product);
    }

    @ApiOperation("添加接口")
    @PostMapping("/add")
    public ResultVO add(@RequestBody Product product){
        return productService.addProduct(product);
    }
}

