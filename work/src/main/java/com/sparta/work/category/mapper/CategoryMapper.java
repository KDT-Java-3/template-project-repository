package com.sparta.work.category.mapper;

import com.sparta.work.category.domain.Category;
import com.sparta.work.category.dto.request.RequestCreateCategoryDto;
import com.sparta.work.category.dto.response.ResponseCategoryDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    Category toEntity(RequestCreateCategoryDto dto);

    ResponseCategoryDto toResponseDto(Category category);
}
