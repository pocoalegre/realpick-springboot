package com.realpick.entity;

import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

@Data
@ToString
public class ShoppingCartVO {

    private Integer cartId;
    private Integer userId;
    private Integer cartNumber;
    private BigDecimal cartPrice;
    private Integer deleted;
    private Date createTime;
    private Date updateTime;

    //åå
    private Product product;

    //ååsku
    private ProductSku productSku;
}
