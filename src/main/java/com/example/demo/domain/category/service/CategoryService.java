package com.example.demo.domain.category.service;

import com.example.demo.domain.category.dto.request.CategoryCreateRequest;
import com.example.demo.domain.category.dto.request.CategoryUpdateRequest;
import com.example.demo.domain.category.dto.response.CategoryResponse;
import com.example.demo.domain.category.entity.Category;
import com.example.demo.domain.category.mapper.CategoryMapper;
import com.example.demo.domain.category.repository.CategoryRepository;
import com.example.demo.global.exception.ServiceException;
import com.example.demo.global.exception.ServiceExceptionCode;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    /**
     * 카테고리 등록
     */
    @Transactional
    public CategoryResponse createCategory(CategoryCreateRequest request) {
        Category category = categoryMapper.toEntity(request);
        Category savedCategory = categoryRepository.save(category);
        return categoryMapper.toResponse(savedCategory);
    }

    /**
     * 모든 카테고리 조회
     */
    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll().stream()
            .map(categoryMapper::toResponse)
            .collect(Collectors.toList());
    }

    /**
     * 단일 카테고리 조회
     */
    public CategoryResponse getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
            .orElseThrow(() -> new ServiceException(ServiceExceptionCode.NOT_FOUND_CATEGORY));
        return categoryMapper.toResponse(category);
    }

    /**
     * 카테고리 수정
     */
    @Transactional
    public CategoryResponse updateCategory(Long id, CategoryUpdateRequest request) {
        Category category = categoryRepository.findById(id)
            .orElseThrow(() -> new ServiceException(ServiceExceptionCode.NOT_FOUND_CATEGORY));

        category.update(request.getName(), request.getDescription());
        return categoryMapper.toResponse(category);
    }

    /**
     * 카테고리 삭제
     */
    @Transactional
    public void deleteCategory(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new ServiceException(ServiceExceptionCode.NOT_FOUND_CATEGORY);
        }
        categoryRepository.deleteById(id);
    }
}
