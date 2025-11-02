package com.sparta.demo.dto.category;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Service Layer에서 사용하는 카테고리 수정 DTO
 */
@Getter
@AllArgsConstructor
public class CategoryUpdateDto {
    private String name;
    private String description;

    public static CategoryUpdateDto from(CategoryRequest request) {
        return new CategoryUpdateDto(
                request.getName(),
                request.getDescription()
        );
    }
}
