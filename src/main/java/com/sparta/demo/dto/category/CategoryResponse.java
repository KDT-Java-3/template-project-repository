package com.sparta.demo.dto.category;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * Controller에서 클라이언트에게 반환하는 카테고리 응답 DTO
 */
@Getter
@AllArgsConstructor
public class CategoryResponse {
    private Long id;
    private String name;
    private String description;
    private Long parentId;
    private String parentName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static CategoryResponse from(CategoryDto dto) {
        return new CategoryResponse(
                dto.getId(),
                dto.getName(),
                dto.getDescription(),
                dto.getParentId(),
                dto.getParentName(),
                dto.getCreatedAt(),
                dto.getUpdatedAt()
        );
    }
}
