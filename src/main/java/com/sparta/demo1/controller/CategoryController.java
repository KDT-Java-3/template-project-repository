package com.sparta.demo1.controller;

import com.sparta.demo1.domain.dto.request.CategoryCreateRequest;
import com.sparta.demo1.domain.dto.request.CategoryUpdateRequest;
import com.sparta.demo1.domain.dto.response.CategoryResponse;
import com.sparta.demo1.domain.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Category", description = "카테고리 관리 API")
@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

  private final CategoryService categoryService;

  @Operation(summary = "카테고리 생성", description = "새로운 카테고리를 생성합니다.")
  @PostMapping
  public ResponseEntity<CategoryResponse> createCategory(@Valid @RequestBody CategoryCreateRequest request) {
    CategoryResponse response = categoryService.createCategory(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @Operation(summary = "카테고리 상세 조회", description = "특정 카테고리의 상세 정보를 조회합니다.")
  @GetMapping("/{categoryId}")
  public ResponseEntity<CategoryResponse> getCategory(@PathVariable Long categoryId) {
    CategoryResponse response = categoryService.getCategory(categoryId);
    return ResponseEntity.ok(response);
  }

  @Operation(summary = "전체 카테고리 목록 조회", description = "모든 카테고리를 조회합니다.")
  @GetMapping
  public ResponseEntity<List<CategoryResponse>> getAllCategories() {
    List<CategoryResponse> responses = categoryService.getAllCategories();
    return ResponseEntity.ok(responses);
  }

  @Operation(summary = "최상위 카테고리 목록 조회", description = "부모가 없는 최상위 카테고리들을 조회합니다.")
  @GetMapping("/top-level")
  public ResponseEntity<List<CategoryResponse>> getTopLevelCategories() {
    List<CategoryResponse> responses = categoryService.getTopLevelCategories();
    return ResponseEntity.ok(responses);
  }

  @Operation(summary = "상품이 있는 카테고리 목록 조회", description = "상품이 등록된 카테고리들을 조회합니다.")
  @GetMapping("/with-products")
  public ResponseEntity<List<CategoryResponse>> getCategoriesWithProducts() {
    List<CategoryResponse> responses = categoryService.getCategoriesWithProducts();
    return ResponseEntity.ok(responses);
  }

  @Operation(summary = "카테고리 수정", description = "카테고리 정보를 수정합니다.")
  @PutMapping("/{categoryId}")
  public ResponseEntity<CategoryResponse> updateCategory(
      @PathVariable Long categoryId,
      @Valid @RequestBody CategoryUpdateRequest request) {
    CategoryResponse response = categoryService.updateCategory(categoryId, request);
    return ResponseEntity.ok(response);
  }

  @Operation(summary = "카테고리 삭제", description = "카테고리를 삭제합니다. 하위 카테고리가 있으면 삭제할 수 없습니다.")
  @DeleteMapping("/{categoryId}")
  public ResponseEntity<Void> deleteCategory(@PathVariable Long categoryId) {
    categoryService.deleteCategory(categoryId);
    return ResponseEntity.noContent().build();
  }
}