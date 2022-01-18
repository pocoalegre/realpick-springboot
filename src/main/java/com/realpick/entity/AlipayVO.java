package com.realpick.entity;

import lombok.Data;

@Data
public class AlipayVO {

    private String orderNumber;
    private String totalPrice;
    private String productName;
    private String orderRemark;

}
