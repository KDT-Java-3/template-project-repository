package com.sprata.sparta_ecommerce.controller;

import com.sprata.sparta_ecommerce.dto.CategoryDetailResponseDto;
import com.sprata.sparta_ecommerce.dto.CategoryRequestDto;
import com.sprata.sparta_ecommerce.dto.CategoryResponseDto;
import com.sprata.sparta_ecommerce.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import com.sprata.sparta_ecommerce.dto.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<ResponseDto<?>> addCategory(@Valid  @RequestBody CategoryRequestDto categoryRequestDto) {
        CategoryResponseDto responseDto = categoryService.addCategory(categoryRequestDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ResponseDto.success(responseDto, "카테고리 추가 성공"));
    }

    @GetMapping("/topSales")
    public ResponseEntity<ResponseDto<?>> getTop10SalesCategories() {
        List<CategoryDetailResponseDto> responseDtos = categoryService.getTop10SalesCategories();
        return ResponseEntity.ok(ResponseDto.success(responseDtos, "Top 10 카테고리 조회 성공"));
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<ResponseDto<?>> updateCategory(@PathVariable Long categoryId, @Valid @RequestBody CategoryRequestDto categoryRequestDto) {
        CategoryResponseDto responseDto = categoryService.updateCategory(categoryId, categoryRequestDto);
        return ResponseEntity.ok(ResponseDto.success(responseDto, "카테고리 수정 성공"));
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ResponseDto<?>> deleteCategory(@PathVariable Long categoryId) {
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.ok(ResponseDto.success(categoryId, "카테고리 수정 성공"));
    }
}