package com.example.demo.domain.category.mapper;

import com.example.demo.domain.category.dto.request.CategoryCreateRequest;
import com.example.demo.domain.category.dto.response.CategoryResponse;
import com.example.demo.domain.category.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    /**
     * CategoryCreateRequest -> Category 엔티티로 변환
     * (parent는 Service에서 별도 설정)
     */
    @Mapping(target = "parent", ignore = true)
    Category toEntity(CategoryCreateRequest request);

    /**
     * Category 엔티티 -> CategoryResponse로 변환
     */
    @Mapping(source = "parent.id", target = "parentId")
    @Mapping(source = "parent.name", target = "parentName")
    CategoryResponse toResponse(Category category);
}
