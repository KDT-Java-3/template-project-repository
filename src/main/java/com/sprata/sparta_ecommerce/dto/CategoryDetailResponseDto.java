package com.sprata.sparta_ecommerce.dto;

import com.sprata.sparta_ecommerce.entity.Category;
import com.sprata.sparta_ecommerce.repository.projection.CategorySalesProjection;
import lombok.Getter;

@Getter
public class CategoryDetailResponseDto {
    private Long id;
    private String name;
    private int salesCount;

    public CategoryDetailResponseDto(CategorySalesProjection data) {
        this.id = data.getCategoryId();
        this.name = data.getCategoryName();
        this.salesCount = data.getCategorySalesCount();
    }
}
