package com.sparta.ecommerce.category.application;

import static com.sparta.ecommerce.category.application.dto.CategoryDto.*;

import com.sparta.ecommerce.category.domain.Category;
import com.sparta.ecommerce.category.infrastructure.CategoryJpaRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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

    @Transactional
    public CategoryResponse updateCategory(Long id, CategoryUpdateRequest patchRequest) {
        Category category = categoryJpaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));

        category.update(patchRequest.getName(), patchRequest.getDescription());

        return CategoryResponse.fromEntity(category);
    }

    @Transactional(readOnly = true)
    public List<CategoryWithProductResponse> getCategories() {
        return categoryJpaRepository.findAll()
                .stream().map(CategoryWithProductResponse::fromEntity).toList();
    }
}
