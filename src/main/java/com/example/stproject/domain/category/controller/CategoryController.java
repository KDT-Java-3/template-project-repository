package com.example.stproject.domain.category.controller;

import com.example.stproject.domain.category.dto.CategoryCreateRequest;
import com.example.stproject.domain.category.dto.CategoryResponse;
import com.example.stproject.domain.category.dto.CategoryUpdateRequest;
import com.example.stproject.domain.category.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/categories")
public class CategoryController {
    private final CategoryService categoryService;

    @Operation(
            summary = "카테고리 등록 API",
            description = "새로운 카테고리를 등록합니다.")
    @PostMapping
    public ResponseEntity<Long> create(@RequestBody @Valid CategoryCreateRequest req) {
        return ResponseEntity.ok(categoryService.create(req));
    }

    @Operation(
            summary = "카테고리 전체 조회 API",
            description = """
                    카테고리를 전체 조회합니다.
                    includeProducts=true 일 경우 카테고리에 포함된 상품 목록도 함께 반환합니다.
            """)
    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getAll(@Parameter(description = "상품 목록 포함 여부 (기본값: false)")
                                                             @RequestParam(defaultValue = "false") boolean includeProducts) {
        return ResponseEntity.ok(categoryService.getAll(includeProducts));
    }

    @Operation(summary = "카테고리 수정 API",
              description = "카테고리 name/description를 수정합니다.")
    @PutMapping
    public ResponseEntity<Long> update(@RequestBody @Valid CategoryUpdateRequest req) {
        return ResponseEntity.ok(categoryService.update(req));
    }
}
