package com.sparta.commerce.management.dto.response.category;

import com.sparta.commerce.management.dto.response.product.ProductResponse;
import com.sparta.commerce.management.entity.Category;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryResponse {

    String id;
    String name;
    String description;
    Category parent;


    public static CategoryResponse getCategory(Category category) {
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .parent(category.getParent())
                .build();
    }

    public static List<CategoryResponse> getCategoryList(List<Category> categoryList) {
        return categoryList.stream()
                .map(CategoryResponse::getCategory)
                .collect(Collectors.toList());
    }
}

