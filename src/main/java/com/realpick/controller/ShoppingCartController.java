package com.realpick.controller;


import com.realpick.entity.ShoppingCart;
import com.realpick.service.ShoppingCartService;
import com.realpick.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 购物车 前端控制器
 * </p>
 *
 * @author pocoalegre
 * @since 2021-10-22
 */

@RestController
@RequestMapping("/shoppingCart")
@Api(value = "购物车操作接口", tags = "购物车管理")
@CrossOrigin
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @ApiOperation("添加接口")
    @PostMapping("/add")
    public ResultVO add(@RequestBody ShoppingCart shoppingCart) {
        return shoppingCartService.addShoppingCart(shoppingCart);
    }

    @ApiOperation("修改接口")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "int", name = "id", value = "购物车id", required = true),
            @ApiImplicitParam(dataType = "int", name = "number", value = "购物车商品数量", required = true)
    })
    @PutMapping("/modify")
    public ResultVO modify(@RequestParam("id") Integer id,
                           @RequestParam("number") Integer number) {
        return shoppingCartService.modifyShoppingCart(id, number);
    }

    @ApiOperation("删除接口")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "int", name = "id", value = "购物车id", required = true)
    })
    @DeleteMapping("/delete")
    public ResultVO delete(@RequestParam("id") Integer id) {
        return shoppingCartService.deleteShoppingCart(id);
    }

    @ApiOperation("列表接口")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "int", name = "pageNum", value = "页码", required = true),
            @ApiImplicitParam(dataType = "int", name = "pageSize", value = "当前页码数据条数", required = true),
            @ApiImplicitParam(dataType = "int", name = "userId", value = "用户id", required = true)
    })
    @GetMapping("/list")
    public ResultVO list(@RequestParam("pageNum") Integer pageNum,
                         @RequestParam("pageSize") Integer pageSize,
                         @RequestParam("userId") Integer userId) {
        return shoppingCartService.ShoppingCartVOList(pageNum, pageSize, userId);
    }

}

