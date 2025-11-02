package com.sparta.sangmin.controller.dto;

import com.sparta.sangmin.domain.Category;

public record CategoryRequest(
        String name,
        String description
) {
    public Category toEntity() {
        return new Category(name, description);
    }
}
