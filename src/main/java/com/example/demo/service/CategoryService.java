package com.example.demo.service;

import com.example.demo.entity.Category;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.service.dto.CategoryCreateRequest;
import com.example.demo.service.dto.CategoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {

    private final CategoryRepository categoryRepository;

    /**
     * 카테고리 등록
     */
    @Transactional
    public CategoryDto createCategory(CategoryCreateRequest request) {
        Category parent = null;
        if (request.getParentId() != null) {
            parent = categoryRepository.findById(request.getParentId())
                    .orElseThrow(() -> new IllegalArgumentException("부모 카테고리를 찾을 수 없습니다. ID: " + request.getParentId()));
        }

        Category category = Category.builder()
                .name(request.getName())
                .description(request.getDescription())
                .parent(parent)
                .build();

        Category savedCategory = categoryRepository.save(category);
        return CategoryDto.fromEntity(savedCategory);
    }

    /**
     * 모든 카테고리 조회
     */
    public List<CategoryDto> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(CategoryDto::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * 단일 카테고리 조회
     */
    public CategoryDto getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("카테고리를 찾을 수 없습니다. ID: " + id));
        return CategoryDto.fromEntity(category);
    }

    /**
     * 카테고리 수정
     */
    @Transactional
    public CategoryDto updateCategory(Long id, CategoryCreateRequest request) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("카테고리를 찾을 수 없습니다. ID: " + id));

        category.update(request.getName(), request.getDescription());

        if (request.getParentId() != null) {
            Category parent = categoryRepository.findById(request.getParentId())
                    .orElseThrow(() -> new IllegalArgumentException("부모 카테고리를 찾을 수 없습니다. ID: " + request.getParentId()));
            category.setParent(parent);
        }

        return CategoryDto.fromEntity(category);
    }

    /**
     * 카테고리 삭제
     */
    @Transactional
    public void deleteCategory(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new IllegalArgumentException("카테고리를 찾을 수 없습니다. ID: " + id);
        }
        categoryRepository.deleteById(id);
    }
}