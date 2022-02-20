package com.realpick.controller;

import com.realpick.service.OrdersService;
import com.realpick.service.UsersService;
import com.realpick.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dashborad")
@Api(value = "仪表板操作接口", tags = "仪表板管理")
@CrossOrigin
public class DashboradController {

    @Autowired
    private UsersService usersService;

    @Autowired
    private OrdersService ordersService;

    @ApiOperation("获取用户数接口")
    @GetMapping("/userCount")
    public ResultVO userCount() {
        return usersService.userCount();
    }

    @ApiOperation("获取成交量接口")
    @GetMapping("/orderCount")
    public ResultVO orderCount() {
        return ordersService.orderCount();
    }

    @ApiOperation("获取销售额接口")
    @GetMapping("/saleAmount")
    public ResultVO saleAmount() {
        return ordersService.saleAmount();
    }
}
