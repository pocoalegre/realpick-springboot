package com.realpick.controller;

import com.realpick.service.OrdersService;
import com.realpick.service.UsersService;
import com.realpick.vo.ResultVO;
import com.realpick.vo.StatusCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;

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

    @ApiOperation("获取订单量接口")
    @GetMapping("/orderCount")
    public ResultVO orderCount() {
        return ordersService.orderCount();
    }

    @ApiOperation("获取销售额接口")
    @GetMapping("/saleAmount")
    public ResultVO saleAmount() {
        return ordersService.saleAmount();
    }

    @ApiOperation("获取浏览量接口")
    @GetMapping("/viewCount")
    public ResultVO viewCount() {

        //获取文件路径
        String targetPath = ClassUtils.getDefaultClassLoader().getResource("view_count").getPath();
        File file = new File(targetPath + "/000000_0");
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String count = reader.readLine();
            return new ResultVO(StatusCode.OK, "获取浏览量成功！", count);
        } catch (Exception e) {
            System.out.println(e);
            return new ResultVO(StatusCode.NO, "获取浏览量失败！", null);
        }
    }

    @ApiOperation("获取月度销量接口")
    @GetMapping("/monthSalesCount")
    public ResultVO monthSalesCount() {
        return ordersService.monthSalesCount();
    }

    @ApiOperation("获取类型销售前五接口")
    @GetMapping("/salesFiveCount")
    public ResultVO salesFiveCount() {
        return ordersService.salesFiveCount();
    }
}
