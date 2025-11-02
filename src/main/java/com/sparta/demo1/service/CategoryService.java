package com.sparta.demo1.service;

import com.sparta.demo1.entity.Category;
import com.sparta.demo1.repository.CategoryRepository;
import com.sparta.demo1.service.dto.CategoryRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private CategoryRepository categoryRepository;

    // 조회
    public List<Category> selectCategory() {
        return categoryRepository.findAll();
    }

    // 카테고리 수정
    public void updateCategory(CategoryRequest categoryRequest) {


        Category category = categoryRepository.findByName(categoryRequest.getName());

        if(category == null){
            throw new RuntimeException("카테고리를 찾을 수 없습니다");
        };

        // null이 아닌 것만 업데이트
        if (categoryRequest.getName() != null) {
            category.setName(categoryRequest.getName());
        }
        if (categoryRequest.getDescription() != null) {
            category.setDescription(categoryRequest.getDescription());
        }

        // 업데이트된 product 저장
        categoryRepository.save(category);
    }

    public void createCategory(CategoryRequest categoryRequest) {

        Category chkCategory = categoryRepository.findByName(categoryRequest.getName());
        if(chkCategory != null){
            throw new RuntimeException("이미 존재하는 카테고리입니다.");
        };

        if (categoryRequest.getName() == null) {
            throw new RuntimeException("카테고리명을 입력해주세요");
        }

        if (categoryRequest.getDescription() == null) {
            throw new RuntimeException("설명을 입력해주세요");
        }


        Category category = Category.builder()
                .name(categoryRequest.getName())
                .description(categoryRequest.getDescription())
                .build();

        categoryRepository.save(category);
    }
}
