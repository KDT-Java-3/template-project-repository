package com.sparta.demo.controller.mapper;

import com.sparta.demo.controller.dto.category.CategoryRequest;
import com.sparta.demo.controller.dto.category.CategoryResponse;
import com.sparta.demo.service.dto.category.CategoryCreateDto;
import com.sparta.demo.service.dto.category.CategoryDto;
import com.sparta.demo.service.dto.category.CategoryUpdateDto;
import org.springframework.stereotype.Component;

/**
 * Category Controller Layer Mapper
 * Request → Service DTO, Service DTO → Response 변환 담당
 */
@Component
public class CategoryControllerMapper {

    /**
     * CategoryRequest를 CategoryCreateDto로 변환
     */
    public CategoryCreateDto toCreateDto(CategoryRequest request) {
        return new CategoryCreateDto(
                request.getName(),
                request.getDescription(),
                request.getParentId()
        );
    }

    /**
     * CategoryRequest를 CategoryUpdateDto로 변환
     */
    public CategoryUpdateDto toUpdateDto(CategoryRequest request) {
        return new CategoryUpdateDto(
                request.getName(),
                request.getDescription()
        );
    }

    /**
     * CategoryDto를 CategoryResponse로 변환
     */
    public CategoryResponse toResponse(CategoryDto dto) {
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
