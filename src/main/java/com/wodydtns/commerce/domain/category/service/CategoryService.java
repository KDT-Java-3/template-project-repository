package com.wodydtns.commerce.domain.category.service;

import java.util.List;

import com.wodydtns.commerce.domain.category.dto.CreateCategoryRequest;
import com.wodydtns.commerce.domain.category.dto.SearchCategoryResponse;
import com.wodydtns.commerce.domain.category.dto.UpdateCategoryRequest;
import com.wodydtns.commerce.domain.category.entity.Category;

public interface CategoryService {

    Category createCategory(CreateCategoryRequest dto);

    Category updateCategory(Long id, UpdateCategoryRequest dto);

    List<SearchCategoryResponse> findAllWithProducts();
}
