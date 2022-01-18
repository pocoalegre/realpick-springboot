package com.realpick.controller;


import com.realpick.entity.Orders;
import com.realpick.service.OrdersService;
import com.realpick.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author pocoalegre
 * @since 2021-10-22
 */

@RestController
@RequestMapping("/order")
@Api(value = "订单操作接口", tags = "订单管理")
@CrossOrigin
public class OrdersController {

    @Autowired
    private OrdersService ordersService;

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
        return ordersService.orderList(queryType, queryInfo, pageNum, pageSize);
    }

    @ApiOperation("单条接口")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "int", name = "id", value = "订单id", required = true)
    })
    @GetMapping("/byId")
    public ResultVO byId(@RequestParam("id") Integer id) {
        return ordersService.orderById(id);
    }

    @ApiOperation("删除接口")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "int", name = "id", value = "订单id", required = true)
    })
    @DeleteMapping("/delete")
    public ResultVO delete(@RequestParam("id") Integer id) {
        return ordersService.deleteOrder(id);
    }

    @ApiOperation("修改接口")
    @PutMapping("/modify")
    public ResultVO modify(@RequestBody Orders order) {
        return ordersService.modifyOrder(order);
    }

    @ApiOperation("添加接口")
    @PostMapping("/add")
    public ResultVO add(@RequestBody Orders order) {
        return ordersService.addOrder(order);
    }

    @ApiOperation("列表接口")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "int", name = "pageNum", value = "页码", required = true),
            @ApiImplicitParam(dataType = "int", name = "pageSize", value = "当前页码数据条数", required = true),
            @ApiImplicitParam(dataType = "int", name = "userId", value = "用户id", required = true)
    })
    @GetMapping("/voList")
    public ResultVO voList(@RequestParam("pageNum") Integer pageNum,
                         @RequestParam("pageSize") Integer pageSize,
                         @RequestParam("userId") Integer userId) {
        return ordersService.orderVOList(pageNum, pageSize, userId);
    }

    @ApiOperation("取消订单接口")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "int", name = "id", value = "订单id", required = true)
    })
    @PutMapping("/cancel")
    public ResultVO cancel(@RequestParam("id") Integer id) {
        return ordersService.cancelOrder(id);
    }

    @ApiOperation("确认订单接口")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "int", name = "id", value = "订单id", required = true)
    })
    @PutMapping("/confirm")
    public ResultVO confirm(@RequestParam("id") Integer id) {
        return ordersService.confirmOrder(id);
    }
}

