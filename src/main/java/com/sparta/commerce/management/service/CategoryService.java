package com.sparta.commerce.management.service;

import com.sparta.commerce.management.dto.request.category.CategoryCreateRequest;
import com.sparta.commerce.management.dto.request.category.CategoryUpdateRequest;
import com.sparta.commerce.management.dto.response.category.CategoryResponse;
import com.sparta.commerce.management.entity.Category;
import com.sparta.commerce.management.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.Objects;
import java.util.UUID;


@Service
@Transactional
@AllArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    //카테고리 신규 저장
    @Transactional
    public CategoryResponse save(CategoryCreateRequest request) {
        Category category = Category.builder()
                            .name(request.getName())
                            .description(request.getDescription())
                        .build();
        return CategoryResponse.getCategory(categoryRepository.save(category));
    }

    //전체 카테고리 조회
    public List<CategoryResponse> findAll(){
        return CategoryResponse.getCategoryList(categoryRepository.findAllByOrderByNameAsc());
    }

    //id로 카테고리 조회
    public CategoryResponse findById(UUID id){
        return CategoryResponse.getCategory(Objects.requireNonNull(categoryRepository.findById(id).orElse(null)));
    }
    
    //이름으로 카테고리 조회
    public CategoryResponse findCategoryByName(String name){
        Category category = categoryRepository.findCategoryByName(name);

        return CategoryResponse.getCategory( category );
    }

    //카테고리 수정
    @Transactional
    public CategoryResponse update(UUID id, CategoryUpdateRequest request){
        //카테고리 조회
        Category category = categoryRepository.findById(id).orElseThrow(() -> new NotFoundException("카테고리가 없습니다"));

        //부모 카테고리 조회
        //Category parent = categoryRepository.findById(request.getParent_id()).orElseThrow(() -> new NotFoundException("부모 카테고리가 없습니다"));

        //카테고리 수정
        category.update(request.getName(), request.getDescription());

        return CategoryResponse.getCategory(category);
    }

}
