package com.realpick.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 用户
 * </p>
 *
 * @author pocoalegre
 * @since 2021-10-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Users对象", description="用户")
public class Users implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户id")
    @TableId(value = "user_id", type = IdType.AUTO)
    private Integer userId;

    @ApiModelProperty(value = "用户名")
    private String userName;

    @ApiModelProperty(value = "用户密码")
    private String userPwd;

    @ApiModelProperty(value = "用户昵称")
    private String userNickname;

    @ApiModelProperty(value = "用户头像")
    private String userImg;

    @ApiModelProperty(value = "用户性别")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String userGender;

    @ApiModelProperty(value = "用户年龄")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String userAge;

    @ApiModelProperty(value = "用户电话")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String userTel;

    @ApiModelProperty(value = "用户邮箱")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String userEmail;

    @ApiModelProperty(value = "删除状态（0：非，1：是）")
    @TableLogic
    private Integer deleted;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

}
