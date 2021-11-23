package com.realpick.controller;


import com.alibaba.fastjson.JSON;
import com.realpick.entity.ProductImg;
import com.realpick.entity.ProductSku;
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
                         @RequestParam("pageSize")Integer pageSize) {
        return productSkuService.productSkuList(queryProductId, pageNum, pageSize);
    }

    @ApiOperation("单条接口")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "int", name = "id", value = "商品skuid", required = true)
    })
    @GetMapping("/byId")
    public ResultVO byId(@RequestParam("id") Integer id){
        return productSkuService.productSkuById(id);
    }

    @ApiOperation("删除接口")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "int", name = "id", value = "商品id", required = true)
    })
    @DeleteMapping("/delete")
    public ResultVO delete(@RequestParam("id") Integer id){
        return productSkuService.deleteProductSku(id);
    }

    @ApiOperation("修改接口")
    @PutMapping("/modify")
    public ResultVO modify(MultipartFile file, String productSku){

        //json对象转换
        ProductSku productSkuByJson = JSON.parseObject(productSku, ProductSku.class);

        //非空字段
        if (file == null){
            return productSkuService.modifyProductSku(productSkuByJson);
        }else {

            //上传文件
            ResultVO saveResultVO = FileManage.fileUpload(file, "static/uploadImg/product/sku");
            if (saveResultVO.getCode() == 10000){
                productSkuByJson.setColorImg((String) saveResultVO.getData());

                //添加商品图片
                return productSkuService.modifyProductSku(productSkuByJson);
            }else {
                return saveResultVO;
            }
        }
    }

    @ApiOperation("添加接口")
    @PostMapping("/add")
    public ResultVO add(MultipartFile file, String productSku){

        //json对象转换
        ProductSku productSkuByJson = JSON.parseObject(productSku, ProductSku.class);

        //非空字段
        if (file == null){
            return new ResultVO(StatusCode.NO, "必须上传图片！", null);
        }else {

            //上传文件
            ResultVO saveResultVO = FileManage.fileUpload(file, "static/uploadImg/product/sku");
            if (saveResultVO.getCode() == 10000){
                System.out.println(productSkuByJson);
                productSkuByJson.setColorImg((String) saveResultVO.getData());

                //添加商品图片
                return productSkuService.addProductSku(productSkuByJson);
            }else {
                return saveResultVO;
            }
        }
    }
}

