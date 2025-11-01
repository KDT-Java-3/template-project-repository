package com.sparta.project.domain.category.service;

import com.sparta.project.domain.category.dto.CategoryCreateRequest;
import com.sparta.project.domain.category.dto.CategoryResponse;
import com.sparta.project.domain.category.dto.CategoryUpdateRequest;
import com.sparta.project.domain.category.entity.Category;
import com.sparta.project.domain.category.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {

    private final CategoryRepository categoryRepository;

    /**
     * 카테고리 등록
     */
    @Transactional
    public CategoryResponse createCategory(CategoryCreateRequest request) {
        // 부모 카테고리 조회 (있는 경우)
        Category parent = null;
        if (request.getParentId() != null) {
            parent = categoryRepository.findById(request.getParentId())
                    .orElseThrow(() -> new IllegalArgumentException("부모 카테고리를 찾을 수 없습니다."));
        }

        // 카테고리 생성
        Category category = Category.builder()
                .name(request.getName())
                .parent(parent)
                .build();

        // 저장
        Category savedCategory = categoryRepository.save(category);

        return CategoryResponse.from(savedCategory);
    }

    /**
     * 모든 카테고리 조회
     */
    public List<CategoryResponse> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return CategoryResponse.fromList(categories);
    }

    /**
     * 특정 카테고리 조회
     */
    public CategoryResponse getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("카테고리를 찾을 수 없습니다."));
        return CategoryResponse.from(category);
    }

    /**
     * 카테고리 수정
     */
    @Transactional
    public CategoryResponse updateCategory(Long id, CategoryUpdateRequest request) {
        // 기존 카테고리 조회
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("카테고리를 찾을 수 없습니다. ID: " + id));

        // 기존 엔티티의 update 메서드 호출
        category.update(request.getName(), request.getDescription());

        return CategoryResponse.from(category);
    }

}
