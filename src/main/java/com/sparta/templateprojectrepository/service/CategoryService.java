package com.sparta.templateprojectrepository.service;

import com.sparta.templateprojectrepository.dto.request.CategoryCreateRequestDto;
import com.sparta.templateprojectrepository.dto.request.CategoryFindRequestDto;
import com.sparta.templateprojectrepository.entity.Category;
import com.sparta.templateprojectrepository.entity.Product;
import com.sparta.templateprojectrepository.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public Category createCategory(CategoryCreateRequestDto dto) {
        Category parentCategory = categoryRepository
                .findById(dto.getParentId()).orElse(null);

        Category category = Category.builder()
                .name(dto.getName())
                .parent(parentCategory)
                .build();

        return categoryRepository.save(category);
    }

    public Category getCategory(CategoryFindRequestDto dto) {

        return categoryRepository.findById(dto.getCategoryId()).orElse(null);

    }

    public Category modifyCategory(CategoryCreateRequestDto dto) {
        Category currentCategory = categoryRepository.findById(dto.getId()).orElseThrow(()-> new NullPointerException("카테고리를 찾을 수 없음"));
        Category parentCategory = categoryRepository.findById(dto.getParentId()).orElse(null);


        Category modifiedCategory = currentCategory.builder()
                .id(currentCategory.getId())
                .name(dto.getName())
                .parent(parentCategory)
                .description(dto.getDescription())
                .build();

        return categoryRepository.save(modifiedCategory);
    }

    public List<Category> getCategoryAll() {

        return categoryRepository.findAll();
    }
}
