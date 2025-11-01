package com.sparta.bootcamp.work.domain.category.service;

import com.sparta.bootcamp.work.domain.category.dto.CategoryCreateRequest;
import com.sparta.bootcamp.work.domain.category.dto.CategoryDto;
import com.sparta.bootcamp.work.domain.category.dto.CategoryEditRequest;
import com.sparta.bootcamp.work.domain.category.entity.Category;
import com.sparta.bootcamp.work.domain.category.repository.CategoryRepository;
import com.sparta.bootcamp.work.domain.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    private final ProductRepository productRepository;

    public Long createCategory(CategoryCreateRequest request){
        Category category = categoryRepository.save(Category.builder()
                        .name(request.getName())
                        .description(request.getDescription())
                .build());
        return category.getId();
    }

    public CategoryDto editCategory(CategoryEditRequest request){
        Category category = categoryRepository.findById(request.getId()).orElseThrow(() -> new RuntimeException("NOT FOUND CATEGORY ID") );
        category.setName(request.getName());
        category.setDescription(request.getDescription());

        return CategoryDto.fromCategory(category);
    }

    public List<CategoryDto> getCategory(){

        List<CategoryDto> result = new ArrayList<>();
        List<Category> categories = categoryRepository.findAll();

        categories.forEach(category -> result.add(CategoryDto.fromCategoryAndProducts(category, productRepository.findByCategory(category))));
        return result;
    }

}
