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
 * 头图
 * </p>
 *
 * @author pocoalegre
 * @since 2021-10-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Banner对象", description="头图")
public class Banner implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "头图id")
    @TableId(value = "banner_id", type = IdType.AUTO)
    private Integer bannerId;

    @ApiModelProperty(value = "头图图片")
    private String bannerImg;

    @ApiModelProperty(value = "头图顺序")
    private Integer bannerSeq;

    @ApiModelProperty(value = "头图类型（1：商品，2：类型）")
    private Integer bannerType;

    @ApiModelProperty(value = "商品id")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Integer productId;

    @ApiModelProperty(value = "商品类型id")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Integer categoryId;

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
