package com.sparta.ecommerce.category.presentation;

import static com.sparta.ecommerce.category.application.dto.CategoryDto.*;

import com.sparta.ecommerce.category.application.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @GetMapping
    public ResponseEntity<List<CategoryWithProductResponse>> getCategories() {
        List<CategoryWithProductResponse> result = categoryService.getCategories();
        return ResponseEntity.ok(result);
    }


    // 카테고리 수정 API
    @PatchMapping("/{id}")
    public ResponseEntity<CategoryResponse> updateCategory(
            @PathVariable Long id,
            @RequestBody @Valid
            CategoryUpdateRequest updateRequest
    ){
        CategoryResponse result = categoryService.updateCategory(id, updateRequest);
        return ResponseEntity.ok(result);
    }

}
