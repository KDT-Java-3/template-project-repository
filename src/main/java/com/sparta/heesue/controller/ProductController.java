package com.sparta.heesue.controller;

import com.sparta.heesue.dto.request.ProductCreateRequestDto;
import com.sparta.heesue.dto.request.ProductUpdateRequestDto;
import com.sparta.heesue.dto.response.ProductResponseDto;
import com.sparta.heesue.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    // 상품 등록
    @PostMapping
    public ResponseEntity<ProductResponseDto> createProduct(
            @RequestBody ProductCreateRequestDto request) {
        ProductResponseDto response = productService.createProduct(request);
        return ResponseEntity.ok(response);
    }

    // 단일 상품 조회
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> getProduct(@PathVariable Long id) {
        ProductResponseDto response = productService.getProduct(id);
        return ResponseEntity.ok(response);
    }

    // 전체 상품 조회 (필터링 포함)
    @GetMapping
    public ResponseEntity<List<ProductResponseDto>> getProducts(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice
    ) {
        List<ProductResponseDto> responses;

        // 카테고리 + 가격 범위
        if (categoryId != null && minPrice != null && maxPrice != null) {
            responses = productService.getProductsByCategoryAndPrice(categoryId, minPrice, maxPrice);
        }
        // 카테고리만
        else if (categoryId != null) {
            responses = productService.getProductsByCategory(categoryId);
        }
        // 가격 범위만
        else if (minPrice != null && maxPrice != null) {
            responses = productService.getProductsByPriceRange(minPrice, maxPrice);
        }
        // 키워드 검색
        else if (keyword != null) {
            responses = productService.searchProducts(keyword);
        }
        // 전체 조회
        else {
            responses = productService.getAllProducts();
        }

        return ResponseEntity.ok(responses);
    }

    // 상품 수정
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDto> updateProduct(
            @PathVariable Long id,
            @RequestBody ProductUpdateRequestDto request) {
        ProductResponseDto response = productService.updateProduct(id, request);
        return ResponseEntity.ok(response);
    }

    // 상품 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}