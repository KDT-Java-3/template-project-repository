package com.sparta.demo.domain.order.service;

import com.sparta.demo.domain.order.dto.request.CreateCategoryRequest;
import com.sparta.demo.domain.order.dto.request.UpdateCategoryRequest;
import com.sparta.demo.domain.order.dto.response.AllCategoryResponse;
import com.sparta.demo.domain.order.dto.response.CategoryResponse;
import com.sparta.demo.domain.order.entity.Category;
import com.sparta.demo.domain.order.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    // 등록
    public CategoryResponse registCategory(CreateCategoryRequest request) throws Exception {
        Category categoryByName = categoryRepository.getCategoryByName(request.getName());
        if(categoryByName != null) {
            throw new Exception("동일한 카테고리가 존재합니다.");
        }

        Category saved = categoryRepository.save(Category.builder()
                .name(request.getName())
                .description(request.getDescription())
                .build());
        return CategoryResponse.buildFromEntity(saved);
    }

    // 조회
    public AllCategoryResponse getAllCategories(){
        List<Category> categories = categoryRepository.findAll();
        return AllCategoryResponse.buildFromEntity(categories);
    }

    // 수정
    public CategoryResponse updateCategory(UpdateCategoryRequest request) throws Exception {
        Category category = categoryRepository.findById(request.getId())
                .orElseThrow(()-> new Exception("카테고리가 존재하지 않습니다."));
        category.updateValues(request);
        return CategoryResponse.buildFromEntity(category);
    }

}
