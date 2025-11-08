package com.sprata.sparta_ecommerce.dto;

import com.sprata.sparta_ecommerce.entity.Category;
import lombok.Getter;

@Getter
public class CategoryDetailResponseDto {
    private Long id;
    private String name;
    private String description;
    private String parentCategoryName;

    public CategoryDetailResponseDto(Category category, Category parentCategory) {
        this.id = category.getId();
        this.name = category.getName();
        this.description = category.getDescription();
        this.parentCategoryName = parentCategory != null ? parentCategory.getName() : "없음";
    }

    public CategoryDetailResponseDto(Category category) {
        this.id = category.getId();
        this.name = category.getName();
        this.description = category.getDescription();
    }
}
