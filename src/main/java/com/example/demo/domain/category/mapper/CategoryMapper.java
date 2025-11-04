package com.example.demo.domain.category.mapper;

import com.example.demo.domain.category.dto.request.CategoryCreateRequest;
import com.example.demo.domain.category.dto.response.CategoryResponse;
import com.example.demo.domain.category.entity.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    /**
     * CategoryCreateRequest -> Category 엔티티로 변환
     */
    Category toEntity(CategoryCreateRequest request);

    /**
     * Category 엔티티 -> CategoryResponse로 변환
     */
    CategoryResponse toResponse(Category category);
}
