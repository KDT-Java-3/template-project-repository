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
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CategoryFindService {
    private final CategoryRepository categoryRepository;

    public Category getById(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NoSuchElementException("category not found"));
    }

    public List<CategoryResponse> getAllCategories() {
        List<Category> categories = categoryRepository.findAllByFetch();

        List<CategoryResponse> categoryTree = new ArrayList<>();
        for (Category category : categories) {
            getCategoryTree(category, categoryTree);
        }

        return categoryTree;
    }

    public List<Long> findAllByParentId(Long parentId) {
        return categoryRepository.findAllByParentId(parentId);
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
}
