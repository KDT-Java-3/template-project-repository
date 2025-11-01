package com.sparta.project1.domain.category.api.dto;

public record CategoryUpdateRequest(
        String name,
        String description
) {
}
