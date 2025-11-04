package com.sparta.ecommerce.domain.category.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CategoryCreateRequest {
    private String name;
    private String description;
}
