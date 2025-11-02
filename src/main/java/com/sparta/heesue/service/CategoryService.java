package com.sparta.heesue.service;

import com.sparta.heesue.dto.request.CategoryRequestDto;
import com.sparta.heesue.dto.response.CategoryResponseDto;
import com.sparta.heesue.entity.Category;
import com.sparta.heesue.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryService {

    private final CategoryRepository categoryRepository;

    // 카테고리 등록
    public CategoryResponseDto createCategory(CategoryRequestDto requestDto) {
        Category parent = null;
        if (requestDto.getParentId() != null) {
            parent = categoryRepository.findById(requestDto.getParentId())
                    .orElseThrow(() -> new IllegalArgumentException("상위 카테고리를 찾을 수 없습니다."));
        }

        Category category = Category.builder()
                .name(requestDto.getName())
                .parent(parent)
                .build();

        Category saved = categoryRepository.save(category);
        return new CategoryResponseDto(saved);
    }

    // 전체 카테고리 조회
    public List<CategoryResponseDto> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(CategoryResponseDto::new)
                .collect(Collectors.toList());
    }

    // 카테고리 수정
    public CategoryResponseDto updateCategory(Long id, CategoryRequestDto requestDto) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 카테고리를 찾을 수 없습니다."));
        category.update(requestDto.getName(), requestDto.getDescription());
        return new CategoryResponseDto(category);
    }
}
