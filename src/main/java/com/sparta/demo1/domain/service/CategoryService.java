package com.sparta.demo1.domain.service;

import com.sparta.demo1.domain.dto.request.CategoryCreateRequest;
import com.sparta.demo1.domain.dto.request.CategoryUpdateRequest;
import com.sparta.demo1.domain.dto.response.CategoryResponse;
import com.sparta.demo1.domain.entity.Category;
import com.sparta.demo1.domain.repository.CategoryRepository;
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

  @Transactional
  public CategoryResponse createCategory(CategoryCreateRequest request) {
    // 부모 카테고리 조회
    Category parent = null;
    if (request.getParentId() != null) {
      parent = categoryRepository.findById(request.getParentId())
          .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 부모 카테고리입니다."));
    }

    // 카테고리 생성
    Category category = Category.builder()
        .name(request.getName())
        .description(request.getDescription())
        .parent(parent)
        .build();

    Category savedCategory = categoryRepository.save(category);
    return CategoryResponse.from(savedCategory);
  }

  public CategoryResponse getCategory(Long categoryId) {
    Category category = categoryRepository.findById(categoryId)
        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 카테고리입니다."));
    return CategoryResponse.from(category);
  }

  public List<CategoryResponse> getAllCategories() {
    List<Category> categories = categoryRepository.findAll();
    return categories.stream()
        .map(CategoryResponse::from)
        .collect(Collectors.toList());
  }

  public List<CategoryResponse> getTopLevelCategories() {
    List<Category> categories = categoryRepository.findByParentIsNull();
    return categories.stream()
        .map(CategoryResponse::from)
        .collect(Collectors.toList());
  }

  public List<CategoryResponse> getCategoriesWithProducts() {
    List<Category> categories = categoryRepository.findCategoriesWithProducts();
    return categories.stream()
        .map(CategoryResponse::from)
        .collect(Collectors.toList());
  }

  @Transactional
  public CategoryResponse updateCategory(Long categoryId, CategoryUpdateRequest request) {
    Category category = categoryRepository.findById(categoryId)
        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 카테고리입니다."));

    // 부모 카테고리 변경
    if (request.getParentId() != null) {
      Category parent = categoryRepository.findById(request.getParentId())
          .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 부모 카테고리입니다."));

      // 자기 자신을 부모로 설정하는 것 방지
      if (parent.getId().equals(categoryId)) {
        throw new IllegalArgumentException("자기 자신을 부모 카테고리로 설정할 수 없습니다.");
      }

      category.updateParent(parent);
    }

    // 나머지 필드 업데이트
    category.updateCategoryInfo(request.getName(), request.getDescription());

    return CategoryResponse.from(category);
  }

  @Transactional
  public void deleteCategory(Long categoryId) {
    Category category = categoryRepository.findById(categoryId)
        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 카테고리입니다."));

    // 하위 카테고리가 있는지 확인
    if (!category.getChildren().isEmpty()) {
      throw new IllegalStateException("하위 카테고리가 있는 카테고리는 삭제할 수 없습니다.");
    }

    categoryRepository.delete(category);
  }
}