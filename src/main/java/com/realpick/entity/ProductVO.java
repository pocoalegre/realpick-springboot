package com.realpick.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class ProductVO {

    private Integer productId;
    private String productImg;
    private String productName;
    private BigDecimal productPrice;

    //类型
    private Category category;

    private String productBrand;
    private Integer productStock;
    private Integer productSales;
    private Integer deleted;
    private Date createTime;
    private Date updateTime;
}
