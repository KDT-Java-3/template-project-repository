package com.sparta.project.service;

import com.sparta.project.dto.CategoryDto;
import com.sparta.project.entity.Category;
import com.sparta.project.exception.DuplicateResourceException;
import com.sparta.project.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Transactional
    public CategoryDto.CategoryResponse createCategory(CategoryDto.CategoryCreateRequest request) {
        // 이름 중복 체크
        if (categoryRepository.existsByName(request.getName())) {
            throw new DuplicateResourceException("이미 존재하는 카테고리 이름입니다: " + request.getName());
        }

        Category category = Category.builder()
                .name(request.getName())
                .description(request.getDescription())
                .build();

        Category savedCategory = categoryRepository.save(category);
        return mapToResponse(savedCategory);
    }

    // Entity -> Response DTO 변환
    private CategoryDto.CategoryResponse mapToResponse(Category category) {
        return CategoryDto.CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .createdAt(category.getCreatedAt())
                .updatedAt(category.getUpdatedAt())
                .build();
    }
}
