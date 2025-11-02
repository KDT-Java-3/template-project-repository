package com.example.stproject.domain.category.mapper;

import com.example.stproject.domain.category.dto.CategoryCreateRequest;
import com.example.stproject.domain.category.dto.CategoryResponse;
import com.example.stproject.domain.category.dto.CategoryUpdateRequest;
import com.example.stproject.domain.category.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    // CREATE: req -> entity
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "products", ignore = true)
    Category toEntity(CategoryCreateRequest req);

    // UPDATE: req -> entity(부분 덮어쓰기, null 무시)
    void updateEntityFromDto(CategoryUpdateRequest req, @MappingTarget Category entity);

    // entity -> response
    // includeProducts = true
    @Mapping(target = "productCount",
            expression = "java(category.getProducts() == null ? 0 : category.getProducts().size())")
    @Mapping(target = "products", source = "products")
    CategoryResponse toResponse(Category category);

    // includeProducts = false
    @Mapping(target = "productCount",
            expression = "java(category.getProducts() == null ? 0 : category.getProducts().size())")
    @Mapping(target = "products", ignore = true)
    CategoryResponse toResponseWithoutProducts(Category category);
}
