package com.example.demo.mapper;

import com.example.demo.controller.dto.CategoryRequestDto;
import com.example.demo.controller.dto.CategoryResponseDto;
import com.example.demo.controller.dto.ProductRequestDto;
import com.example.demo.entity.Category;
import com.example.demo.service.dto.CategoryServiceInputDto;
import com.example.demo.service.dto.ProductServiceInputDto;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = false))
public interface CategoryMapper {

    @Mapping(target = "description", expression = "java(normalizeDescription(req.getDescription()))")
    CategoryServiceInputDto toService(CategoryRequestDto req);

    List<CategoryResponseDto> toResponseList(List<Category> products);
}
