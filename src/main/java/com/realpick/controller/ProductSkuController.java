package com.realpick.controller;


import com.alibaba.fastjson.JSON;
import com.realpick.entity.ProductSku;
import com.realpick.entity.ProductSkuVO;
import com.realpick.service.ProductSkuService;
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
 * sku 前端控制器
 * </p>
 *
 * @author pocoalegre
 * @since 2021-10-22
 */

@RestController
@RequestMapping("/productSku")
@Api(value = "商品sku操作接口", tags = "商品sku管理")
@CrossOrigin
public class ProductSkuController {

    @Autowired
    private ProductSkuService productSkuService;

    @ApiOperation("条件查询分页列表接口")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "string", name = "queryProductId", value = "商品编号"),
            @ApiImplicitParam(dataType = "int", name = "pageNum", value = "页码", required = true),
            @ApiImplicitParam(dataType = "int", name = "pageSize", value = "当前页码数据条数", required = true)
    })
    @GetMapping("/list")
    public ResultVO list(@RequestParam("queryProductId") String queryProductId,
                         @RequestParam("pageNum") Integer pageNum,
                         @RequestParam("pageSize") Integer pageSize) {
        return productSkuService.productSkuList(queryProductId, pageNum, pageSize);
    }

    @ApiOperation("单条接口")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "int", name = "id", value = "商品skuid", required = true)
    })
    @GetMapping("/byId")
    public ResultVO byId(@RequestParam("id") Integer id) {
        return productSkuService.productSkuById(id);
    }

    @ApiOperation("删除接口")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "int", name = "id", value = "商品id", required = true)
    })
    @DeleteMapping("/delete")
    public ResultVO delete(@RequestParam("id") Integer id) {
        return productSkuService.deleteProductSku(id);
    }

    @ApiOperation("修改接口")
    @PutMapping("/modify")
    public ResultVO modify(@RequestBody ProductSku productSku) {
        return productSkuService.modifyProductSku(productSku);
    }

    @ApiOperation("添加接口")
    @PostMapping("/add")
    public ResultVO add(@RequestBody ProductSku productSku) {
        return productSkuService.addProductSku(productSku);
    }

    @ApiOperation("单条商品sku接口")
    @GetMapping("/byProductId")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "int", name = "id", value = "商品id", required = true)
    })
    public ResultVO byProductId(@RequestParam("id") Integer id) {
        return productSkuService.productSkuByProductId(id);
    }

    @ApiOperation("商品sku尺寸列表接口")
    @GetMapping("/skuSizeList")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "int", name = "id", value = "商品id", required = true)
    })
    public ResultVO skuSizeList(@RequestParam("id") Integer id) {
        return productSkuService.productSkuSizeList(id);
    }

    @ApiOperation("商品sku颜色列表接口")
    @GetMapping("/skuColorList")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "int", name = "id", value = "商品id", required = true)
    })
    public ResultVO skuColorList(@RequestParam("id") Integer id) {
        return productSkuService.productSkuColorList(id);
    }

    @ApiOperation("re商品sku颜色列表接口")
    @GetMapping("/reSkuColorList")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "int", name = "id", value = "商品id", required = true),
            @ApiImplicitParam(dataType = "string", name = "skuSize", value = "sku尺寸", required = true)
    })
    public ResultVO reSkuColorList(@RequestParam("id") Integer id,
                                   @RequestParam("skuSize") String skuSize) {
        return productSkuService.reProductSkuColorList(id, skuSize);
    }

    @ApiOperation("re商品sku尺寸列表接口")
    @GetMapping("/reSkuSizeList")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "int", name = "id", value = "商品id", required = true),
            @ApiImplicitParam(dataType = "string", name = "skuColor", value = "sku颜色", required = true)
    })
    public ResultVO reSkuSizeList(@RequestParam("id") Integer id,
                                  @RequestParam("skuColor") String skuColor) {
        return productSkuService.reProductSkuSizeList(id, skuColor);
    }

    @ApiOperation("查询id接口")
    @PostMapping("/selectId")
    public ResultVO selectId(@RequestBody ProductSkuVO productSkuVO) {
        return productSkuService.selectIdByThree(productSkuVO);
    }
}

