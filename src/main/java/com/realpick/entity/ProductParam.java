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
 * 商品参数
 * </p>
 *
 * @author pocoalegre
 * @since 2021-10-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="ProductParam对象", description="商品参数")
public class ProductParam implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "参数id")
    @TableId(value = "param_id", type = IdType.AUTO)
    private Integer paramId;

    @ApiModelProperty(value = "商品id")
    private Integer productId;

    @ApiModelProperty(value = "参数属性")
    private String paramKey;

    @ApiModelProperty(value = "参数值")
    private String paramValue;

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
