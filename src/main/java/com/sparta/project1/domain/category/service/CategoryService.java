package com.sparta.project1.domain.category.service;

import com.sparta.project1.domain.category.api.dto.CategoryRegisterRequest;
import com.sparta.project1.domain.category.api.dto.CategoryResponse;
import com.sparta.project1.domain.category.api.dto.CategoryUpdateRequest;
import com.sparta.project1.domain.category.domain.Category;
import com.sparta.project1.domain.category.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public void register(CategoryRegisterRequest request) {
        Category parent = null;
        if (request.parentId() != null) {
            parent = categoryRepository.findById(request.parentId())
                    .orElseThrow(() -> new NoSuchElementException("parent category not found"));
        }

        Category category = Category.register(request.name(), request.description(), parent);

        categoryRepository.save(category);
    }

    @Transactional(readOnly = true)
    public List<CategoryResponse> getAllCategories() {
        List<Category> categories = categoryRepository.findAllByFetch();

        List<CategoryResponse> categoryTree = new ArrayList<>();
        for (Category category : categories) {
            getCategoryTree(category, categoryTree);
        }

        return categoryTree;
    }

    public void getCategoryTree(Category category, List<CategoryResponse> categoryResponse) {
        if (category.getParent() == null) {
            categoryResponse.add(new CategoryResponse(category.getId(), category.getName(), new ArrayList<>()));
        } else {
            for (CategoryResponse response : categoryResponse) {
                if (response.id().equals(category.getParent().getId())) {
                    response.childCategory().add(new CategoryResponse(category.getId(), category.getName(), new ArrayList<>()));
                    return;
                } else {
                    getCategoryTree(category, response.childCategory());
                }
            }
        }
    }

    public void updateCategory(Long id, CategoryUpdateRequest request) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("category not found"));
        category.updateCategory(request.name(), request.description());

        categoryRepository.save(category);
    }
}
