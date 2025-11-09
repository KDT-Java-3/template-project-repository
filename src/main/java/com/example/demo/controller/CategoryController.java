package com.example.demo.controller;

import com.example.demo.common.ApiResponse;
import com.example.demo.controller.dto.CategoryResponseDto;
import com.example.demo.controller.dto.CategoryRequestDto;
import com.example.demo.controller.dto.CategoryResponseDto;
import com.example.demo.mapper.CategoryMapper;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.service.CategoryService;
import com.example.demo.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorys")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    // 전체 카테고리 조회
//    @GetMapping
//    public ApiResponse<List<CategoryResponseDto>> getAll() {
//        return ApiResponse.success(CategoryService.getAll());
//    }

    // 카테고리 생성
//    @PostMapping
//    public ResponseEntity<ApiResponse<CategoryResponseDto>> create(
//            @Valid @RequestBody CategoryRequestDto request
//    ) {
//        CategoryResponseDto response = categoryService.create(categoryMapper.toService(request));
//        return ApiResponse.created(response);
//    }

    // 카테고리 수정
//    @PutMapping("/{id}")
//    public ApiResponse<CategoryResponseDto> update(@PathVariable Long id,
//                                                  @Valid @RequestBody CategoryRequestDto request) {
//        return ApiResponse.success(categoryService.update(id, categoryMapper.toService(request)));
//    }
//
//    // 카테고리 삭제
//    @DeleteMapping("/{id}")
//    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
//        CategoryService.delete(id);
//        return ResponseEntity.ok(ApiResponse.success());
//    }
}
