package com.realpick.controller;


import com.realpick.entity.UserAddr;
import com.realpick.service.UserAddrService;
import com.realpick.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 用户地址 前端控制器
 * </p>
 *
 * @author pocoalegre
 * @since 2021-10-22
 */

@RestController
@RequestMapping("/userAddr")
@Api(value = "用户地址操作接口", tags = "用户地址管理")
@CrossOrigin
public class UserAddrController {

    @Autowired
    private UserAddrService userAddrService;

    @ApiOperation("条件查询分页列表接口")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "int", name = "id", value = "用户id", required = true),
            @ApiImplicitParam(dataType = "int", name = "pageNum", value = "页码", required = true),
            @ApiImplicitParam(dataType = "int", name = "pageSize", value = "当前页码数据条数", required = true)
    })
    @GetMapping("/list")
    public ResultVO list(@RequestParam("id") Integer id,
                         @RequestParam("pageNum") Integer pageNum,
                         @RequestParam("pageSize") Integer pageSize) {
        return userAddrService.userAddrList(id, pageNum, pageSize);
    }

    @ApiOperation("单条接口")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "int", name = "id", value = "用户地址id", required = true)
    })
    @GetMapping("/byId")
    public ResultVO byId(Integer id) {
        return userAddrService.userAddrById(id);
    }

    @ApiOperation("添加接口")
    @PostMapping("add")
    public ResultVO add(@RequestBody UserAddr userAddr) {
        return userAddrService.addUserAddr(userAddr);
    }

    @ApiOperation("修改接口")
    @PutMapping("modify")
    public ResultVO modify(@RequestBody UserAddr userAddr) {
        return userAddrService.modifyUserAddr(userAddr);
    }

    @ApiOperation("删除接口")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "int", name = "id", value = "用户地址id", required = true)
    })
    @DeleteMapping("/delete")
    public ResultVO delete(@RequestParam("id") Integer id) {
        return userAddrService.deleteUserAddr(id);
    }
}

