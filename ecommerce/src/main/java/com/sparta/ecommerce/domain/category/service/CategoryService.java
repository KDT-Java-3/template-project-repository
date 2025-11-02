package com.sparta.ecommerce.domain.category.service;

import com.sparta.ecommerce.domain.category.dto.CategoryCreateRequest;
import com.sparta.ecommerce.domain.category.dto.CategoryResponse;
import com.sparta.ecommerce.domain.category.dto.CategoryUpdateRequest;
import com.sparta.ecommerce.domain.category.entity.Category;
import com.sparta.ecommerce.domain.category.repository.CategoryRepository;
import java.util.List;
import lombok.Generated;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Transactional
    public CategoryResponse createCategory(CategoryCreateRequest dto) {
        Category category = Category.builder().name(dto.getName()).description(dto.getDescription()).build();
        Category saved = (Category)this.categoryRepository.save(category);
        return CategoryResponse.fromEntity(saved);
    }

    @Transactional(
            readOnly = true
    )
    public List<CategoryResponse> getAllCategories() {
        return this.categoryRepository.findAll().stream().map(CategoryResponse::fromEntity).toList();
    }

    @Transactional
    public CategoryResponse updateCategory(CategoryUpdateRequest dto) {
        Category category = (Category)this.categoryRepository.findById(dto.getId()).orElseThrow(() -> new IllegalArgumentException("카테고리 없음"));
        category.update(dto.getName(), dto.getDescription());
        return CategoryResponse.fromEntity(category);
    }

    @Generated
    public CategoryService(final CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
}
