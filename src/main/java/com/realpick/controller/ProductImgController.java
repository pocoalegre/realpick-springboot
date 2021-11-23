package com.realpick.controller;


import com.alibaba.fastjson.JSON;
import com.realpick.entity.ProductImg;
import com.realpick.service.ProductImgService;
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
 * 商品图片 前端控制器
 * </p>
 *
 * @author pocoalegre
 * @since 2021-10-22
 */

@RestController
@RequestMapping("/productImg")
@Api(value = "商品图片操作接口", tags = "商品图片管理")
@CrossOrigin
public class ProductImgController {
    
    @Autowired
    private ProductImgService productImgService;

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
        return productImgService.productImgList(queryProductId, pageNum, pageSize);
    }

    @ApiOperation("单条接口")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "int", name = "id", value = "商品图片id", required = true)
    })
    @GetMapping("/byId")
    public ResultVO byId(@RequestParam("id") Integer id){
        return productImgService.productImgById(id);
    }

    @ApiOperation("删除接口")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "int", name = "id", value = "商品id", required = true)
    })
    @DeleteMapping("/delete")
    public ResultVO delete(@RequestParam("id") Integer id){
        return productImgService.deleteProductImg(id);
    }

    @ApiOperation("修改接口")
    @PutMapping("/modify")
    public ResultVO modify(MultipartFile file, String productImg){

        //json对象转换
        ProductImg productImgByJson = JSON.parseObject(productImg, ProductImg.class);

        //非空字段
        if (file == null){
            return productImgService.modifyProductImg(productImgByJson);
        }else {

            //上传文件
            ResultVO saveResultVO = FileManage.fileUpload(file, "static/uploadImg/product/img");
            if (saveResultVO.getCode() == 10000){
                productImgByJson.setImgUrl((String) saveResultVO.getData());

                //添加商品图片
                return productImgService.modifyProductImg(productImgByJson);
            }else {
                return saveResultVO;
            }
        }
    }

    @ApiOperation("添加接口")
    @PostMapping("/add")
    public ResultVO add(MultipartFile file, String productImg){

        //json对象转换
        ProductImg productImgByJson = JSON.parseObject(productImg, ProductImg.class);

        //非空字段
        if (file == null){
            return new ResultVO(StatusCode.NO, "必须上传图片！", null);
        }else {

            //上传文件
            ResultVO saveResultVO = FileManage.fileUpload(file, "static/uploadImg/product/img");
            if (saveResultVO.getCode() == 10000){
                productImgByJson.setImgUrl((String) saveResultVO.getData());

                //添加商品图片
                return productImgService.addProductImg(productImgByJson);
            }else {
                return saveResultVO;
            }
        }
    }
}

