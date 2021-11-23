package com.realpick.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 * 订单
 * </p>
 *
 * @author pocoalegre
 * @since 2021-10-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Orders对象", description="订单")
public class Orders implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "订单id")
    @TableId(value = "order_id", type = IdType.AUTO)
    private Integer orderId;

    @ApiModelProperty(value = "订单号")
    private String orderNumber;

    @ApiModelProperty(value = "用户id")
    private Integer userId;

    @ApiModelProperty(value = "收件人姓名")
    private String receiverName;

    @ApiModelProperty(value = "收件人电话")
    private String receiverTel;

    @ApiModelProperty(value = "收件人地址")
    private String receiverAddr;

    @ApiModelProperty(value = "支付方式（1：支付宝）")
    private Integer payType;

    @ApiModelProperty(value = "总价")
    private BigDecimal totalAmount;

    @ApiModelProperty(value = "实际支付")
    private BigDecimal actualAmount;

    @ApiModelProperty(value = "订单备注")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String orderRemark;

    @ApiModelProperty(value = "提交时间")
    private Date submitTime;

    @ApiModelProperty(value = "取消时间")
    private Date cancelTime;

    @ApiModelProperty(value = "支付时间")
    private Date payTime;

    @ApiModelProperty(value = "发货时间")
    private Date deliverTime;

    @ApiModelProperty(value = "收货时间")
    private Date receiveTime;

    @ApiModelProperty(value = "完成时间")
    private Date finishTime;

    @ApiModelProperty(value = "订单状态（0：已取消，1：待支付，2：待发货，3：待收货，4：已完成）")
    private Integer status;

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
