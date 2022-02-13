package com.realpick.entity;

import lombok.Data;

import java.util.List;

@Data
public class CategoryVO {

    private Integer categoryId;
    private String categoryName;
    private Integer categoryLevel;

    private List<Category> categoryList;
}
