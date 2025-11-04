package com.example.demo.service;

import com.example.demo.controller.dto.CategoryRequest;
import com.example.demo.controller.dto.CategoryResponse;
import com.example.demo.entity.Category;
import com.example.demo.entity.Product;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ProductRepository;
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
    private final ProductRepository productRepository;

    @Transactional
    public CategoryResponse createCategory(CategoryRequest request) {
        Category category = Category.builder()
                .name(request.getName())
                .description(request.getDescription())
                .build();

        Category savedCategory = categoryRepository.save(category);
        return toResponse(savedCategory);
    }

    public CategoryResponse getCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("카테고리를 찾을 수 없습니다."));
        return toResponse(category);
    }

    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public CategoryResponse updateCategory(Long id, CategoryRequest request) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("카테고리를 찾을 수 없습니다."));

        category.update(request.getName(), request.getDescription());

        return toResponse(category);
    }

    private CategoryResponse toResponse(Category category) {
        List<Product> products = productRepository.findByCategory(category);

        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .parent(category.getParent() != null ? CategoryResponse.CategoryInfo.builder()
                        .id(category.getParent().getId())
                        .name(category.getParent().getName())
                        .build() : null)
                .children(category.getChildren().stream()
                        .map(child -> CategoryResponse.CategoryInfo.builder()
                                .id(child.getId())
                                .name(child.getName())
                                .build())
                        .collect(Collectors.toList()))
                .products(products.stream()
                        .map(product -> CategoryResponse.ProductInfo.builder()
                                .id(product.getId())
                                .name(product.getName())
                                .build())
                        .collect(Collectors.toList()))
                .createdAt(category.getCreatedAt())
                .updatedAt(category.getUpdatedAt())
                .build();
    }
}

