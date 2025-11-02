package com.sparta.week01project.domain.category.service;

import com.sparta.week01project.domain.category.controller.request.CategoryCreateRequest;
import com.sparta.week01project.domain.category.dto.CategoryDto;
import com.sparta.week01project.domain.category.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryDto createCategory(CategoryDto request) {
//        categoryRepository.save()
        return null;
    }
}
