package com.sparta.ecommerce.domain.category.dto;

import com.sparta.ecommerce.domain.category.entity.Category;
import lombok.Builder;
import lombok.Generated;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CategoryResponse {
    private Long id;
    private String name;
    private String description;

    public static CategoryResponse fromEntity(Category category) {
        return builder().id(category.getId()).name(category.getName()).description(category.getDescription()).build();
    }
}
