package com.sparta.demo.category.controller.response;

import com.sparta.demo.category.domain.Category;

public record CategoryFindAllResponse(
        Long id,
        String name,
        String description
) {

    public static CategoryFindAllResponse of(Category category) {
        return new CategoryFindAllResponse(
                category.getId(),
                category.getName(),
                category.getDescription()
        );
    }
}
