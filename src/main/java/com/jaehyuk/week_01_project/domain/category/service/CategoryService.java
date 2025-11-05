package com.jaehyuk.week_01_project.domain.category.service;

import com.jaehyuk.week_01_project.domain.category.dto.CategoryResponse;
import com.jaehyuk.week_01_project.domain.category.dto.CreateCategoryRequest;
import com.jaehyuk.week_01_project.domain.category.dto.UpdateCategoryRequests;
import com.jaehyuk.week_01_project.domain.category.entity.Category;
import com.jaehyuk.week_01_project.domain.category.repository.CategoryRepository;
import com.jaehyuk.week_01_project.exception.custom.CategoryNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Transactional
    public Long createCategory(CreateCategoryRequest request) {

        Category parent = request.parentId() == null ? null
                : categoryRepository.findById(request.parentId()).orElseThrow(() -> {
                    log.warn("부모 카테고리를 찾을 수 없음 - parentId: {}", request.parentId());
                    return new CategoryNotFoundException(
                            "부모 카테고리를 찾을 수 없습니다. ID: " + request.parentId()
                    );
                });

        Category category = Category.builder()
                .name(request.name())
                .description(request.description())
                .parent(parent)
                .build();

        Category savedCategory = categoryRepository.save(category);

        log.info("카테고리 생성 완료 - categoryId: {}, name: {}, parentId: {}", savedCategory.getId(), request.name(), request.parentId());

        return savedCategory.getId();
    }

    @Transactional
    public Long updateCategory(Long categoryId, @Valid UpdateCategoryRequests request) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(EntityNotFoundException::new);

        category.update(request.name(), request.description());

        return categoryRepository.save(category).getId();
    }

    public List<CategoryResponse> getAllCategoriesHierarchy() {
        List<Category> allCategories = categoryRepository.findAll();

        Map<Long, CategoryResponse> categoryMap = allCategories.stream().collect(Collectors.toMap(
                Category::getId,
                CategoryResponse::from
        ));

        Map<Long, List<CategoryResponse>> childrenMap = new HashMap<>();
        for (Category category : allCategories) {
            if (category.getParent() != null) {
                Long parentId = category.getParent().getId();
                childrenMap.computeIfAbsent(parentId, k -> new ArrayList<>())
                        .add(categoryMap.get(category.getId()));
            }
        }

        List<CategoryResponse> rootCategories = new ArrayList<>();
        for (Category category : allCategories) {
            CategoryResponse response = categoryMap.get(category.getId());
            List<CategoryResponse> children = childrenMap.getOrDefault(category.getId(), new ArrayList<>());

            if (!children.isEmpty()) {
                response = response.withChildren(children);
                categoryMap.put(category.getId(), response);
            }

            if (category.getParent() == null) {
                rootCategories.add(response);
            }
        }

        log.info("카테고리 계층 구조 조회 완료 - 전체: {}, 최상위: {}", allCategories.size(), rootCategories.size());

        return rootCategories;
    }
}
