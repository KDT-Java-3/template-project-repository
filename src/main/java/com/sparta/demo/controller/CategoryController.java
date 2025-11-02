package com.sparta.demo.controller;

import com.sparta.demo.dto.category.*;
import com.sparta.demo.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Category", description = "카테고리 관리 API")
@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @Operation(summary = "카테고리 등록", description = "새로운 카테고리를 등록합니다.")
    @PostMapping
    public ResponseEntity<CategoryResponse> createCategory(@Valid @RequestBody CategoryRequest request) {
        CategoryCreateDto dto = CategoryCreateDto.from(request);
        CategoryDto categoryDto = categoryService.createCategory(dto);
        CategoryResponse response = CategoryResponse.from(categoryDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "카테고리 단건 조회", description = "ID로 카테고리를 조회합니다.")
    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> getCategory(@PathVariable Long id) {
        CategoryDto categoryDto = categoryService.getCategory(id);
        CategoryResponse response = CategoryResponse.from(categoryDto);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "카테고리 전체 조회", description = "모든 카테고리를 조회합니다.")
    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getAllCategories() {
        List<CategoryDto> categoryDtos = categoryService.getAllCategories();
        List<CategoryResponse> responses = categoryDtos.stream()
                .map(CategoryResponse::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "카테고리 수정", description = "기존 카테고리 정보를 수정합니다.")
    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse> updateCategory(
            @PathVariable Long id,
            @Valid @RequestBody CategoryRequest request) {
        CategoryUpdateDto dto = CategoryUpdateDto.from(request);
        CategoryDto categoryDto = categoryService.updateCategory(id, dto);
        CategoryResponse response = CategoryResponse.from(categoryDto);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "카테고리 삭제", description = "카테고리를 삭제합니다.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}
