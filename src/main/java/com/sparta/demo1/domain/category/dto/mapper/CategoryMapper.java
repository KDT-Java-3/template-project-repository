package com.sparta.demo1.domain.category.dto.mapper;

import com.sparta.demo1.domain.category.dto.response.CategoryResDto;
import com.sparta.demo1.domain.category.entity.CategoryEntity;
import com.sparta.demo1.domain.product.dto.response.ProductResDto;
import com.sparta.demo1.domain.product.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    @Mapping(target = "parentId", source = "parent.id") // parent.id → parentId 매핑
    CategoryResDto.CategorySimpleInfo toRes(CategoryEntity categoryEntity);
}
