package com.example.demo.domain.category.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateCategoryRequestDto {

    private String name;
    private String description;
}
