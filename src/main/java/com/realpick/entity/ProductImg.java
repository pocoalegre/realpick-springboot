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
 * 商品图片
 * </p>
 *
 * @author pocoalegre
 * @since 2021-10-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="ProductImg对象", description="商品图片")
public class ProductImg implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "图片id")
    @TableId(value = "img_id", type = IdType.AUTO)
    private Integer imgId;

    @ApiModelProperty(value = "商品id")
    private Integer productId;

    @ApiModelProperty(value = "图片路径")
    private String imgUrl;

    @ApiModelProperty(value = "图片顺序")
    private Integer imgSeq;

    @ApiModelProperty(value = "是否主图（1：是，0：否）")
    private Integer isMain;

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
