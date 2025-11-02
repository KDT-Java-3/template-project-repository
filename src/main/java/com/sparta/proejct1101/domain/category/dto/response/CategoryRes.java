package com.sparta.proejct1101.domain.category.dto.response;

import com.sparta.proejct1101.domain.category.entity.Category;

public record CategoryRes (
        String parentName,
        String name,
        String description)
{

    public static CategoryRes from(Category category)
    {
        return new CategoryRes(
                category.getParent() != null ? category.getParent().getName() : null,
                category.getName(),
                category.getDescription()
        );
    }
}
