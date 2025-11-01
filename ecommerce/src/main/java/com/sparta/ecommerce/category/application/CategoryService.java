package com.sparta.ecommerce.category.application;

import static com.sparta.ecommerce.category.application.dto.CategoryDto.*;

import com.sparta.ecommerce.category.domain.Category;
import com.sparta.ecommerce.category.infrastructure.CategoryJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryJpaRepository categoryJpaRepository;

    @Transactional
    public CategoryResponse createCategory(CategoryCreateRequest createRequest) {
        Category newCategory = createRequest.toEntity();
        Category savedCategory = categoryJpaRepository.save(newCategory);
        return CategoryResponse.fromEntity(savedCategory);
    }
}
