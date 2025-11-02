package com.example.demo.controller;

import com.example.demo.service.ProductService;
import com.example.demo.service.dto.ProductCreateRequest;
import com.example.demo.service.dto.ProductDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Tag(name = "Product", description = "상품 관리 API")
@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @Operation(summary = "상품 등록", description = "새로운 상품을 등록합니다")
    @PostMapping
    public ResponseEntity<ProductDto> createProduct(
            @Valid @RequestBody ProductCreateRequest request) {
        ProductDto response = productService.createProduct(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "모든 상품 조회", description = "등록된 모든 상품을 조회합니다")
    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        List<ProductDto> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @Operation(summary = "상품 상세 조회", description = "ID로 특정 상품을 조회합니다")
    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id) {
        ProductDto product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    @Operation(summary = "상품 수정", description = "기존 상품 정보를 수정합니다")
    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody ProductCreateRequest request) {
        ProductDto response = productService.updateProduct(id, request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "상품 삭제", description = "상품을 삭제합니다")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "카테고리별 상품 조회", description = "특정 카테고리의 상품들을 조회합니다")
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<ProductDto>> getProductsByCategory(
            @Parameter(description = "카테고리 ID") @PathVariable Long categoryId) {
        List<ProductDto> products = productService.getProductsByCategory(categoryId);
        return ResponseEntity.ok(products);
    }

    @Operation(summary = "상품명 검색", description = "상품명 키워드로 상품을 검색합니다")
    @GetMapping("/search")
    public ResponseEntity<List<ProductDto>> searchProductsByName(
            @Parameter(description = "검색 키워드") @RequestParam String keyword) {
        List<ProductDto> products = productService.searchProductsByName(keyword);
        return ResponseEntity.ok(products);
    }

    @Operation(summary = "가격 범위 검색", description = "가격 범위로 상품을 검색합니다")
    @GetMapping("/price-range")
    public ResponseEntity<List<ProductDto>> getProductsByPriceRange(
            @Parameter(description = "최소 가격") @RequestParam BigDecimal minPrice,
            @Parameter(description = "최대 가격") @RequestParam BigDecimal maxPrice) {
        List<ProductDto> products = productService.getProductsByPriceRange(minPrice, maxPrice);
        return ResponseEntity.ok(products);
    }

    @Operation(summary = "복합 검색", description = "카테고리, 가격 범위, 상품명으로 복합 검색합니다")
    @GetMapping("/search/advanced")
    public ResponseEntity<List<ProductDto>> searchProducts(
            @Parameter(description = "카테고리 ID (선택)") @RequestParam(required = false) Long categoryId,
            @Parameter(description = "최소 가격 (선택)") @RequestParam(required = false) BigDecimal minPrice,
            @Parameter(description = "최대 가격 (선택)") @RequestParam(required = false) BigDecimal maxPrice,
            @Parameter(description = "검색 키워드 (선택)") @RequestParam(required = false) String keyword) {
        List<ProductDto> products = productService.searchProducts(categoryId, minPrice, maxPrice, keyword);
        return ResponseEntity.ok(products);
    }
}