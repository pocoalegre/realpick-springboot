package com.realpick.controller;


import com.realpick.entity.Delivery;
import com.realpick.service.DeliveryService;
import com.realpick.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 快递 前端控制器
 * </p>
 *
 * @author pocoalegre
 * @since 2021-10-22
 */

@RestController
@RequestMapping("/delivery")
@Api(value = "快递操作接口", tags = "快递管理")
@CrossOrigin
public class DeliveryController {

    @Autowired
    private DeliveryService deliveryService;

    @ApiOperation("添加接口")
    @PostMapping("add")
    public ResultVO add(@RequestBody Delivery delivery) {
        return deliveryService.addDelivery(delivery);
    }

    @ApiOperation("修改接口")
    @PutMapping("modify")
    public ResultVO modify(@RequestBody Delivery delivery) {
        return deliveryService.modifyDelivery(delivery);
    }

    @ApiOperation("删除接口")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "int", name = "id", value = "快递id", required = true)
    })
    @DeleteMapping("/delete")
    public ResultVO delete(@RequestParam("id") Integer id) {
        return deliveryService.deleteDelivery(id);
    }

    @ApiOperation("条件查询分页列表接口")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "string", name = "queryCom", value = "快递公司"),
            @ApiImplicitParam(dataType = "string", name = "queryType", value = "查询类型"),
            @ApiImplicitParam(dataType = "string", name = "queryInfo", value = "查询内容"),
            @ApiImplicitParam(dataType = "int", name = "pageNum", value = "页码", required = true),
            @ApiImplicitParam(dataType = "int", name = "pageSize", value = "当前页码数据条数", required = true)
    })
    @GetMapping("/list")
    public ResultVO list(@RequestParam("queryCom") String queryCom,
                         @RequestParam("queryType") String queryType,
                         @RequestParam("queryInfo") String queryInfo,
                         @RequestParam("pageNum") Integer pageNum,
                         @RequestParam("pageSize") Integer pageSize) {
        return deliveryService.deliveryList(queryCom, queryType, queryInfo, pageNum, pageSize);
    }

    @ApiOperation("单条接口")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "int", name = "id", value = "快递id", required = true)
    })
    @GetMapping("/byId")
    public ResultVO byId(Integer id) {
        return deliveryService.deliveryById(id);
    }
}

