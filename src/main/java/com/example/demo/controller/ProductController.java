package com.example.demo.controller;


import com.example.demo.common.ApiResponse;
import com.example.demo.controller.dto.ProductRequestDto;
import com.example.demo.controller.dto.ProductResponseDto;
import com.example.demo.mapper.ProductMapper;
import com.example.demo.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    // prefix = /api/products

    private final ProductService productService;
    private final ProductMapper productMapper;

    // 전체 상품 조회
    @GetMapping
    public ApiResponse<List<ProductResponseDto>> getAll() {
        return ApiResponse.success(productService.getAll());
    }

    // 단일 상품 조회
    @GetMapping("/{id}")
    public ApiResponse<ProductResponseDto> getById(@PathVariable Long id) {
        return ApiResponse.success(productService.getById(id));
    }

    // 상품 생성
    // ProductRequestDto -> ProductServiceInputDto
    // ProductServiceInputDto -> Entity
    // Entity -> ProductServiceOutputDto
    // ProductServiceOutputDto -> ProductResponseDto
    @PostMapping
    public ResponseEntity<ApiResponse<ProductResponseDto>> create(
            @Valid @RequestBody ProductRequestDto request
    ) {
        ProductResponseDto response = productService.create(productMapper.toService(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(response));
    }

    // 상품 수정
    @PutMapping("/{id}")
    public ApiResponse<ProductResponseDto> update(@PathVariable Long id,
                                                  @Valid @RequestBody ProductRequestDto request) {
        return ApiResponse.success(productService.update(id, productMapper.toService(request)));
    }

    // 상품 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.ok(ApiResponse.success());
    }
}
