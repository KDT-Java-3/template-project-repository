package com.sparta.demo1.service.dto;

import com.sparta.demo1.entity.Category;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductRequest {
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stock;
    private Category category;
}
