package com.sparta.demo1.domain.controller;

import com.sparta.demo1.domain.dto.request.ProductCreateRequest;
import com.sparta.demo1.domain.dto.request.ProductSearchRequest;
import com.sparta.demo1.domain.dto.request.ProductUpdateRequest;
import com.sparta.demo1.domain.dto.response.ProductResponse;
import com.sparta.demo1.domain.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Product", description = "상품 관리 API")
@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

  private final ProductService productService;

  @Operation(summary = "상품 생성", description = "새로운 상품을 생성합니다.")
  @PostMapping
  public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody ProductCreateRequest request) {
    ProductResponse response = productService.createProduct(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @Operation(summary = "상품 상세 조회", description = "특정 상품의 상세 정보를 조회합니다.")
  @GetMapping("/{productId}")
  public ResponseEntity<ProductResponse> getProduct(@PathVariable Long productId) {
    ProductResponse response = productService.getProduct(productId);
    return ResponseEntity.ok(response);
  }

  @Operation(summary = "전체 상품 목록 조회", description = "모든 상품을 조회합니다.")
  @GetMapping
  public ResponseEntity<List<ProductResponse>> getAllProducts() {
    List<ProductResponse> responses = productService.getAllProducts();
    return ResponseEntity.ok(responses);
  }

  @Operation(summary = "상품 검색", description = "키워드, 가격 범위, 카테고리로 상품을 검색합니다.")
  @PostMapping("/search")
  public ResponseEntity<List<ProductResponse>> searchProducts(@RequestBody ProductSearchRequest request) {
    List<ProductResponse> responses = productService.searchProducts(request);
    return ResponseEntity.ok(responses);
  }

  @Operation(summary = "상품 수정", description = "상품 정보를 수정합니다.")
  @PutMapping("/{productId}")
  public ResponseEntity<ProductResponse> updateProduct(
      @PathVariable Long productId,
      @Valid @RequestBody ProductUpdateRequest request) {
    ProductResponse response = productService.updateProduct(productId, request);
    return ResponseEntity.ok(response);
  }

  @Operation(summary = "상품 삭제", description = "상품을 삭제합니다.")
  @DeleteMapping("/{productId}")
  public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {
    productService.deleteProduct(productId);
    return ResponseEntity.noContent().build();
  }
}