package com.wodydtns.commerce.domain.category.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdateCategoryDto {
    private String name;
    private String description;
}
