package com.sparta.project.domain.category.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CategoryUpdateRequest {
    private String name;
    private String description;
}
