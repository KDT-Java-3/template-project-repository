package com.sparta.hodolee246.week01project.service;

import com.sparta.hodolee246.week01project.entity.Category;
import com.sparta.hodolee246.week01project.entity.Product;
import com.sparta.hodolee246.week01project.repository.CategoryRepository;
import com.sparta.hodolee246.week01project.service.request.CategoryDto;
import com.sparta.hodolee246.week01project.service.response.CategoryInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional
    public CategoryInfo addCategory(CategoryDto categoryDto) {
        Category category = Category.builder()
                .name(categoryDto.name())
                .description(categoryDto.description())
                .build();

        Category savedCategory = categoryRepository.save(category);

        return savedCategory.toResponse();
    }

    public List<CategoryInfo> getCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(Category::toResponse)
                .toList();
    }

    @Transactional
    public CategoryInfo updateCategory(Long categoryId, CategoryDto categoryDto) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("category not found"));

        category.updateCategory(categoryDto);

        return category.toResponse();
    }

}
