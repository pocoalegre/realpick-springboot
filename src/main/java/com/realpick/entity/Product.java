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
 * 商品
 * </p>
 *
 * @author pocoalegre
 * @since 2021-10-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Product对象", description="商品")
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "商品id")
    @TableId(value = "product_id", type = IdType.AUTO)
    private Integer productId;

    @ApiModelProperty(value = "商品名")
    private String productName;

    @ApiModelProperty(value = "商品类型id")
    private Integer categoryId;

    @ApiModelProperty(value = "商品品牌")
    private String productBrand;

    @ApiModelProperty(value = "商品描述")
    private String productDesc;

    @ApiModelProperty(value = "商品销量")
    private Integer productSales;

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
