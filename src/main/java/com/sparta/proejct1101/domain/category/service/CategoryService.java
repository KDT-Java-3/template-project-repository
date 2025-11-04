package com.sparta.proejct1101.domain.category.service;

import com.sparta.proejct1101.domain.category.dto.request.CategoryReq;
import com.sparta.proejct1101.domain.category.dto.response.CategoryRes;

import java.util.List;

public interface CategoryService {

    CategoryRes createCategory(CategoryReq req);

    List<CategoryRes> getCategory();

    CategoryRes updateCategory(CategoryReq req);
}
