package com.wodydtns.commerce.domain.category.service.impl;

import com.wodydtns.commerce.domain.category.dto.CreateCategoryRequest;
import com.wodydtns.commerce.domain.category.dto.SearchCategoryResponse;
import com.wodydtns.commerce.domain.category.dto.UpdateCategoryRequest;
import com.wodydtns.commerce.domain.category.entity.Category;
import com.wodydtns.commerce.domain.category.repository.CategoryRepository;
import com.wodydtns.commerce.domain.category.service.CategoryService;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryServiceImpl implements CategoryService {

        private final CategoryRepository categoryRepository;

        @Override
        @Transactional
        public Category createCategory(CreateCategoryRequest dto) {
                Category category = Category.builder()
                                .name(dto.getName())
                                .description(dto.getDescription())
                                .build();
                return categoryRepository.save(category);
        }

        @Override
        @Transactional
        public Category updateCategory(Long id, UpdateCategoryRequest dto) {
                Category category = categoryRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException("카테고리를 찾을 수 없습니다."));
                category = Category.builder()
                                .id(category.getId())
                                .name(dto.getName())
                                .description(dto.getDescription())
                                .build();
                return categoryRepository.save(category);
        }

        @Override
        public List<SearchCategoryResponse> findAllWithProducts() {
                List<Category> categories = categoryRepository.findAllWithProducts();

                return categories.stream()
                                .map(category -> SearchCategoryResponse.builder()
                                                .name(category.getName())
                                                .description(category.getDescription())
                                                .products(category.getProducts())
                                                .build())
                                .collect(Collectors.toList());
        }
}
