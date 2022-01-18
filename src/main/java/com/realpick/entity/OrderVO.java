package com.realpick.entity;

import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

@Data
@ToString
public class OrderVO {

    private Integer orderId;
    private String orderNumber;
    private Integer userId;
    private String receiverName;
    private String receiverTel;
    private String receiverAddr;
    private Integer payType;
    private BigDecimal totalAmount;
    private BigDecimal actualAmount;
    private String orderRemark;
    private Date submitTime;
    private Date cancelTime;
    private Date payTime;
    private Date deliverTime;
    private Date receiveTime;
    private Date finishTime;
    private Integer status;
    private Integer deleted;
    private Date createTime;
    private Date updateTime;

    //订单明细
    private OrderDetail orderDetail;
}
