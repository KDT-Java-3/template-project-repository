package com.sparta.demo.category.service.command;

import com.sparta.demo.category.domain.Category;

public record CategorySaveCommand(
        String name,
        String description
) {

    public Category toEntity() {
        return Category.create(name, description);
    }
}
