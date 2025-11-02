package com.sparta.ecommerce.product.presentation;

import com.sparta.ecommerce.product.application.ProductService;
import static com.sparta.ecommerce.product.application.dto.ProductDto.*;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductApiController {

    private final ProductService productService;

    // 상품 등록
    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(
            @RequestBody @Valid
            ProductCreateRequest createRequest
    ){
        ProductResponse result = productService.createProduct(createRequest);
        return ResponseEntity.ok(result);
    }

    // 단일 상품 조회
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable Long id) {
        ProductResponse result = productService.getProduct(id);
        return ResponseEntity.ok(result);
    }

    // 검색 필터링, 카테고리, 가격 범위, 상품명 키워드
    // 전체 상품 조회
    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts(
            ProductSearchRequest searchRequest
    ) {
        List<ProductResponse> result = productService.getProducts(searchRequest);
        return ResponseEntity.ok(result);
    }

    // 상품 수정.
    @PatchMapping("{id}")
    public ResponseEntity<ProductResponse> updateProduct(
            @PathVariable Long id,
            @RequestBody ProductUpdateRequest updateRequest
    ){
        ProductResponse result = productService.updateProduct(id, updateRequest);
        return ResponseEntity.ok(result);
    }


}
