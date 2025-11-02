package com.sparta.demo1.service.dto;

import com.sparta.demo1.entity.Category;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CategoryRequest {
    private String name;
    private String description;
}
