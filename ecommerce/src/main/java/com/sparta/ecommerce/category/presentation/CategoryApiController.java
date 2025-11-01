package com.sparta.ecommerce.category.presentation;

import static com.sparta.ecommerce.category.application.dto.CategoryDto.*;

import com.sparta.ecommerce.category.application.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryApiController {

    private final CategoryService categoryService;

    // 카테고리 등록 API
    @PostMapping
    public ResponseEntity<CategoryResponse> createCategory(
            @RequestBody @Valid
            CategoryCreateRequest createRequest
    ) {
        CategoryResponse result = categoryService.createCategory(createRequest);
        return ResponseEntity.ok(result);
    }

    // 모든 카테고리 조회 API


    // 카테고리 수정 API



}
