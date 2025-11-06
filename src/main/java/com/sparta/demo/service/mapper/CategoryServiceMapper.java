package com.sparta.demo.service.mapper;

import com.sparta.demo.domain.category.Category;
import com.sparta.demo.service.dto.category.CategoryDto;
import org.springframework.stereotype.Component;

/**
 * Category Service Layer Mapper
 * Entity → DTO 변환 담당
 */
@Component
public class CategoryServiceMapper {

    /**
     * Category Entity를 CategoryDto로 변환
     */
    public CategoryDto toDto(Category category) {
        return new CategoryDto(
                category.getId(),
                category.getName(),
                category.getDescription(),
                category.getParent() != null ? category.getParent().getId() : null,
                category.getParent() != null ? category.getParent().getName() : null,
                category.getCreatedAt(),
                category.getUpdatedAt()
        );
    }
}
