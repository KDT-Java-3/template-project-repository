package com.sparta.demo.controller;

import com.sparta.demo.dto.product.*;
import com.sparta.demo.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Product", description = "상품 관리 API")
@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @Operation(summary = "상품 등록", description = "새로운 상품을 등록합니다.")
    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody ProductRequest request) {
        // Request → DTO 변환
        ProductCreateDto dto = ProductCreateDto.from(request);

        // Service 호출
        ProductDto productDto = productService.createProduct(dto);

        // DTO → Response 변환
        ProductResponse response = ProductResponse.from(productDto);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "상품 단건 조회", description = "ID로 상품을 조회합니다.")
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable Long id) {
        ProductDto productDto = productService.getProduct(id);
        ProductResponse response = ProductResponse.from(productDto);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "상품 전체 조회", description = "모든 상품을 조회합니다.")
    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        List<ProductDto> productDtos = productService.getAllProducts();
        List<ProductResponse> responses = productDtos.stream()
                .map(ProductResponse::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "상품 검색",
               description = "검색 조건에 따라 상품을 조회합니다. 검색 조건: 카테고리, 가격 범위, 상품명 키워드")
    @GetMapping("/search")
    public ResponseEntity<List<ProductResponse>> searchProducts(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) String keyword) {
        List<ProductDto> productDtos = productService.searchProducts(categoryId, minPrice, maxPrice, keyword);
        List<ProductResponse> responses = productDtos.stream()
                .map(ProductResponse::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "상품 수정", description = "기존 상품 정보를 수정합니다.")
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody ProductRequest request) {
        // Request → DTO 변환
        ProductUpdateDto dto = ProductUpdateDto.from(request);

        // Service 호출
        ProductDto productDto = productService.updateProduct(id, dto);

        // DTO → Response 변환
        ProductResponse response = ProductResponse.from(productDto);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "상품 삭제", description = "상품을 삭제합니다.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
