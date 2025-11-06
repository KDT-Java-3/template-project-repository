package com.sparta.bootcamp.java_2_example.service;

import com.sparta.bootcamp.java_2_example.common.enums.ErrorCode;
import com.sparta.bootcamp.java_2_example.domain.category.entity.Category;
import com.sparta.bootcamp.java_2_example.domain.category.repository.CategoryRepository;
import com.sparta.bootcamp.java_2_example.dto.request.CategoryRequest;
import com.sparta.bootcamp.java_2_example.dto.response.CategoryResponse;
import com.sparta.bootcamp.java_2_example.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional
    public CategoryResponse createCategory(CategoryRequest request) {

        Category parentCategory = null;

        if (request.getParentId() != null && request.getParentId() > 0) {
            parentCategory = categoryRepository.findById(request.getParentId())
                    .orElseThrow(() -> new CustomException(
                            ErrorCode.NOT_FOUND,
                            "상위 카테고리가 존재하지 않습니다. ID: " + request.getParentId()
                    ));
        }

        Category category = Category.builder()
                .name(request.getName())
                .parent(parentCategory)
                .build();

        Category savedCategory = categoryRepository.save(category);
        log.info("카테고리 등록 완료: ID {}", savedCategory.getId());

        return CategoryResponse.from(savedCategory);
    }

    public CategoryResponse getCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND, "해당 카테고리를 찾을 수 없습니다. ID: " + id));
        return CategoryResponse.from(category);
    }

    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(CategoryResponse::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public CategoryResponse updateCategory(Long id, CategoryRequest request) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND, "해당 카테고리를 찾을 수 없습니다. ID: " + id));

        Category parent = null;
        if (request.getParentId() != null) {
            parent = categoryRepository.findById(request.getParentId())
                    .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND, "상위 카테고리를 찾을 수 없습니다. ID: " + request.getParentId()));
        }

        category.update(request.getName(), request.getDescription(), parent);

        return CategoryResponse.from(category);
    }

    @Transactional
    public void deleteCategory(Long id) {
        log.info("카테고리 삭제 요청: ID {}", id);

        if (!categoryRepository.existsById(id)) {
            throw new CustomException(ErrorCode.NOT_FOUND, "해당 카테고리를 찾을 수 없습니다. ID: " + id);
        }

        categoryRepository.deleteById(id);
        log.info("카테고리 삭제 완료: ID {}", id);
    }
}
