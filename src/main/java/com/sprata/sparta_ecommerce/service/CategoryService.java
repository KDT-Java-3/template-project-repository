package com.sprata.sparta_ecommerce.service;

import com.sprata.sparta_ecommerce.dto.CategoryRequestDto;
import com.sprata.sparta_ecommerce.dto.CategoryResponseDto;
import com.sprata.sparta_ecommerce.dto.param.PageDto;

import java.util.List;

public interface CategoryService {
    CategoryResponseDto addCategory(CategoryRequestDto categoryRequestDto);

    List<CategoryResponseDto> getAllCategories(PageDto pageDto);

    CategoryResponseDto updateCategory(Long categoryId, CategoryRequestDto categoryRequestDto);
}