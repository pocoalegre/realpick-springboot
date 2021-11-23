package com.realpick.controller;


import com.alibaba.druid.util.StringUtils;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.realpick.entity.Users;
import com.realpick.service.UsersService;
import com.realpick.utils.FileManage;
import com.realpick.vo.ResultVO;
import com.realpick.vo.StatusCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 用户 前端控制器
 * </p>
 *
 * @author pocoalegre
 * @since 2021-10-22
 */

@RestController
@RequestMapping("/user")
@Api(value = "用户操作接口", tags = "用户管理")
@CrossOrigin
public class UsersController {
    
    @Autowired
    private UsersService usersService;

    @Autowired
    private DefaultKaptcha defaultKaptcha;

    @Autowired
    RedisTemplate redisTemplate;

    @ApiOperation("注册接口")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "string", name = "name", value = "用户名", required = true),
            @ApiImplicitParam(dataType = "string", name = "pwd", value = "用户密码", required = true)
    })
    @PostMapping("/regist")
    public ResultVO regist(@RequestParam("name") String name,
                           @RequestParam("pwd") String pwd){
        return usersService.userRegist(name, pwd);
    }

    @ApiOperation("创建验证码接口")
    @GetMapping("/createCode")
    public void createCode(HttpServletResponse response) throws IOException {
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");

        // 生成文字验证码
        String text = defaultKaptcha.createText();

        // 生成图片验证码
        BufferedImage image = defaultKaptcha.createImage(text);

        // 使用redis缓存验证码的值，并设置过期时间为60秒
        redisTemplate.opsForValue().set("imgCode",text,60, TimeUnit.SECONDS);
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "jpg", out);
        out.flush();
        out.close();
    }

    @ApiOperation("登录接口")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "string", name = "name", value = "用户名", required = true),
            @ApiImplicitParam(dataType = "string", name = "pwd", value = "用户密码", required = true),
            @ApiImplicitParam(dataType = "string", name = "code", value = "验证码", required = true),
    })
    @GetMapping("/login")
    public ResultVO login(@RequestParam("name") String name,
                          @RequestParam("pwd") String pwd,
                          @RequestParam("code") String verificationCode){
        //判断时效
        if(!redisTemplate.hasKey("imgCode")) {
            return new ResultVO(StatusCode.NO, "验证码已过期！", null);
        }

        //判断输入，成功则进行账号密码判断
        String code = redisTemplate.opsForValue().get("imgCode").toString();
        if(StringUtils.equals(verificationCode,code)) {
            return usersService.userLogin(name, pwd);
        } else {
            return new ResultVO(StatusCode.NO, "验证码输入错误！", null);
        }
    }

    @ApiOperation("自查信息接口")
    @ApiImplicitParams(
            @ApiImplicitParam(dataType = "int", name = "id", value = "用户id", required = true)
    )
    @GetMapping("/byId")
    public ResultVO byId(@RequestParam("id") Integer id){
        return usersService.userById(id);
    }

    @ApiOperation("修改信息接口")
    @PutMapping("/modify")
    public ResultVO modify(@RequestBody Users user){
        return usersService.userModify(user);
    }

    @ApiOperation("修改密码接口")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "int", name = "id", value = "用户id", required = true),
            @ApiImplicitParam(dataType = "string", name = "pwd", value = "用户输入密码", required = true),
            @ApiImplicitParam(dataType = "string", name = "newPwd", value = "修改后密码", required = true)
    })
    @PutMapping("/pwdModify")
    public ResultVO pwdModify(@RequestParam("id") Integer id,
                              @RequestParam("pwd") String pwd,
                              @RequestParam("newPwd") String newPwd){
        return usersService.userPwdModify(id, pwd, newPwd);
    }

    @ApiOperation("管理员修改密码接口")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "int", name = "id", value = "用户id", required = true),
            @ApiImplicitParam(dataType = "string", name = "newPwd", value = "修改后密码", required = true)
    })
    @PutMapping("/pwdModifyByAdmin")
    public ResultVO pwdModifyByAdmin(@RequestParam("id") Integer id,
                              @RequestParam("newPwd") String newPwd){
        return usersService.userPwdModifyByAdmin(id, newPwd);
    }

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
                         @RequestParam("pageSize")Integer pageSize){
        return usersService.userList(queryType, queryInfo, pageNum, pageSize);
    }

    @ApiOperation("删除接口")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "int", name = "id", value = "用户id", required = true)
    })
    @DeleteMapping("/delete")
    public ResultVO delete(@RequestParam("id") Integer id){
        return usersService.userDelete(id);
    }

    @ApiOperation("更改头像接口")
    @PutMapping("/uploadImg")
    public ResultVO uploadImg(MultipartFile file, Integer id){
        ResultVO saveResultVO = FileManage.fileUpload(file, "static/uploadImg/userHead");
        if (saveResultVO.getCode() == 10000){
            return usersService.userImgModify(id, (String) saveResultVO.getData());
        }else{
            return saveResultVO;
        }
    }
    
}

