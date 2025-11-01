package com.sparta.project1.domain.category.api.dto;

import java.util.List;

public record CategoryResponse(
        Long id, String name, List<CategoryResponse> childCategory
) {
}
