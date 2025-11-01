package com.pepponechoi.project.domain.category.service.impl;

import com.pepponechoi.project.domain.category.dto.request.CategoryCreateRequest;
import com.pepponechoi.project.domain.category.dto.request.CategoryUpdateRequest;
import com.pepponechoi.project.domain.category.dto.response.CategoryResponse;
import com.pepponechoi.project.domain.category.entity.Category;
import com.pepponechoi.project.domain.category.repository.CategoryRepository;
import com.pepponechoi.project.domain.category.service.CategoryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;


    @Override
    @Transactional
    public CategoryResponse create(CategoryCreateRequest request) {
        Category category = Category.builder()
            .name(request.getName())
            .description(request.getDescription())
            .build();
        categoryRepository.save(category);

        return new CategoryResponse(category.getId(), category.getName(), category.getDescription());
    }

    @Override
    public List<CategoryResponse> getAll() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map((Category c) -> new CategoryResponse(c.getId(),
            c.getName(), c.getDescription())).toList();
    }

    @Override
    public CategoryResponse getById(Long id) {
        Category category = categoryRepository.findById(id).orElse(null);
        if (category == null) {
            // 원래는 예외를 던져야 함.
            return null;
        }
        return new CategoryResponse(category.getId(), category.getName(), category.getDescription());
    }

    @Override
    @Transactional
    public void update(Long id, CategoryUpdateRequest request) {
        Category category = categoryRepository.findById(id).orElse(null);
        if (category == null) {
            // 원래는 예외를 던져야 함.
        }
        category.update(request.getName(), request.getDescription());
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Category category = categoryRepository.findById(id).orElse(null);
        if (category == null) {
            // 원래는 예외를 던져야 함
        }
        categoryRepository.delete(category);
    }
}
