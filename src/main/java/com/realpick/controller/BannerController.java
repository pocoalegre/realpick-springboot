package com.realpick.controller;


import com.alibaba.fastjson.JSON;
import com.realpick.entity.Banner;
import com.realpick.service.BannerService;
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
 * 头图 前端控制器
 * </p>
 *
 * @author pocoalegre
 * @since 2021-10-22
 */

@RestController
@RequestMapping("/banner")
@Api(value = "Banner操作接口", tags = "Banner管理")
@CrossOrigin
public class BannerController {

    @Autowired
    private BannerService bannerService;

    @ApiOperation("添加接口")
    @PostMapping("add")
    public ResultVO add(MultipartFile file, String banner) {

        //json对象转换
        Banner bannerByJson = JSON.parseObject(banner, Banner.class);

        //非空字段
        if (file == null) {
            return new ResultVO(StatusCode.NO, "必须上传图片！", null);
        } else {

            //上传文件
            ResultVO saveResultVO = FileManage.fileUpload(file, "static/uploadImg/banner");
            if (saveResultVO.getCode() == 10000) {
                bannerByJson.setBannerImg((String) saveResultVO.getData());

                //添加banner
                return bannerService.addBanner(bannerByJson);
            } else {
                return saveResultVO;
            }
        }
    }

    @ApiOperation("修改接口")
    @PutMapping("modify")
    public ResultVO modify(MultipartFile file, String banner) {

        //json对象转换
        Banner bannerByJson = JSON.parseObject(banner, Banner.class);
        if (file == null) {
            return bannerService.modifyBanner(bannerByJson);
        } else {

            //上传文件
            ResultVO saveResultVO = FileManage.fileUpload(file, "static/uploadImg/banner");
            bannerByJson.setBannerImg((String) saveResultVO.getData());
            if (saveResultVO.getCode() == 10000) {
                bannerByJson.setBannerImg((String) saveResultVO.getData());

                //修改banner
                return bannerService.modifyBanner(bannerByJson);
            } else {
                return saveResultVO;
            }
        }
    }

    @ApiOperation("条件查询分页列表接口")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "string", name = "queryType", value = "查询类型"),
            @ApiImplicitParam(dataType = "int", name = "pageNum", value = "页码", required = true),
            @ApiImplicitParam(dataType = "int", name = "pageSize", value = "当前页码数据条数", required = true)
    })
    @GetMapping("/list")
    public ResultVO list(@RequestParam("queryType") String queryType,
                         @RequestParam("pageNum") Integer pageNum,
                         @RequestParam("pageSize") Integer pageSize) {
        return bannerService.bannerList(queryType, pageNum, pageSize);
    }

    @ApiOperation("删除接口")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "int", name = "id", value = "bannerid", required = true)
    })
    @DeleteMapping("/delete")
    public ResultVO delete(@RequestParam("id") Integer id) {
        return bannerService.deleteBanner(id);
    }

    @ApiOperation("单条接口")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "int", name = "id", value = "bannerid", required = true)
    })
    @GetMapping("/byId")
    public ResultVO byId(@RequestParam("id") Integer id) {
        return bannerService.bannerById(id);
    }

    @ApiOperation("首页展示列表接口")
    @GetMapping("/indexList")
    public ResultVO indexList() {
        return bannerService.bannerIndexList();
    }
}

