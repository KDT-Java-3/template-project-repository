package com.ynv.ecommerce.controller;

import com.ynv.ecommerce.controller.dto.ProductRequest;
import com.ynv.ecommerce.service.ProductService;
import com.ynv.ecommerce.service.dto.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private ProductService productService;

    // 1. 상품 등록
    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody ProductRequest dto) {
        productService.createProduct(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // 2. 단일 상품 상세 조회
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProduct(id));
    }

    // 3. 다건 조회 (검색 및 필터링 적용)

}
