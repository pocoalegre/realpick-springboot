package com.realpick.controller;


import com.realpick.entity.ProductParam;
import com.realpick.service.ProductParamService;
import com.realpick.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 商品参数 前端控制器
 * </p>
 *
 * @author pocoalegre
 * @since 2021-10-22
 */

@RestController
@RequestMapping("/productParam")
@Api(value = "商品参数操作接口", tags = "商品参数管理")
@CrossOrigin
public class ProductParamController {

    @Autowired
    private ProductParamService productParamService;

    @ApiOperation("条件查询分页列表接口")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "string", name = "queryProductId", value = "商品编号"),
            @ApiImplicitParam(dataType = "int", name = "pageNum", value = "页码", required = true),
            @ApiImplicitParam(dataType = "int", name = "pageSize", value = "当前页码数据条数", required = true)
    })
    @GetMapping("/list")
    public ResultVO list(@RequestParam("queryProductId") String queryProductId,
                         @RequestParam("pageNum") Integer pageNum,
                         @RequestParam("pageSize")Integer pageSize) {
        return productParamService.productParamList(queryProductId, pageNum, pageSize);
    }

    @ApiOperation("单条接口")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "int", name = "id", value = "商品参数id", required = true)
    })
    @GetMapping("/byId")
    public ResultVO byId(@RequestParam("id") Integer id){
        return productParamService.productParamById(id);
    }

    @ApiOperation("删除接口")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "int", name = "id", value = "商品id", required = true)
    })
    @DeleteMapping("/delete")
    public ResultVO delete(@RequestParam("id") Integer id){
        return productParamService.deleteProductParam(id);
    }

    @ApiOperation("修改接口")
    @PutMapping("/modify")
    public ResultVO modify(@RequestBody ProductParam productParam){
        return productParamService.modifyProductParam(productParam);
    }

    @ApiOperation("添加接口")
    @PostMapping("/add")
    public ResultVO add(@RequestBody ProductParam productParam){
        return productParamService.addProductParam(productParam);
    }
}

