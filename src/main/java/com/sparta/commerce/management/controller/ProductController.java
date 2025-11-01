package com.sparta.commerce.management.controller;

import com.sparta.commerce.management.dto.request.product.ProductRequest;
import com.sparta.commerce.management.dto.request.product.ProductSearchRequest;
import com.sparta.commerce.management.dto.response.product.ProductResponse;
import com.sparta.commerce.management.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productService;

    //상품 등록 API
    @PostMapping("/save")
    public ResponseEntity<ProductResponse> save(@RequestBody @Valid ProductRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.save(request));
    }

    //상품 조회 API
    @PostMapping("/search")
    public ResponseEntity<List<ProductResponse>> findAllByUserId(@RequestBody @Valid ProductSearchRequest request){
        return ResponseEntity.ok(productService.searchProducts(request));
    }

    //상품 수정 API
    @PutMapping("/update/{id}")
    public ResponseEntity<ProductResponse> update(@PathVariable UUID id, @RequestBody @Valid ProductRequest request){
        return ResponseEntity.ok(productService.update(id,request));
    }
}
