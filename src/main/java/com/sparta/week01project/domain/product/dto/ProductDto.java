package com.sparta.week01project.domain.product.dto;


import com.sparta.week01project.domain.category.entity.Category;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ProductDto {
    private Long id;
    private String name;
    private String description;
    private int price;
    private int stock;
    private Category category;
    private Long categoryId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
