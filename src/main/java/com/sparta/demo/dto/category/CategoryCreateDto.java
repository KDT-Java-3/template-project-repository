package com.sparta.demo.dto.category;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Service Layer에서 사용하는 카테고리 생성 DTO
 */
@Getter
@AllArgsConstructor
public class CategoryCreateDto {
    private String name;
    private String description;
    private Long parentId;  // 부모 카테고리 ID (선택사항)

    public static CategoryCreateDto from(CategoryRequest request) {
        return new CategoryCreateDto(
                request.getName(),
                request.getDescription(),
                request.getParentId()
        );
    }
}
