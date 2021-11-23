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
 * 快递
 * </p>
 *
 * @author pocoalegre
 * @since 2021-10-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Delivery对象", description="快递")
public class Delivery implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "快递id")
    @TableId(value = "delivery_id", type = IdType.AUTO)
    private Integer deliveryId;

    @ApiModelProperty(value = "订单id")
    private Integer orderId;

    @ApiModelProperty(value = "快递单号")
    private String deliveryNu;

    @ApiModelProperty(value = "快递公司")
    private String deliveryCom;

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
