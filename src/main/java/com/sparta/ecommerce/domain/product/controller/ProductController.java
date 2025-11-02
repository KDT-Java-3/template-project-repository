package com.sparta.ecommerce.domain.product.controller;

import com.sparta.ecommerce.domain.product.dto.ProductDto;
import com.sparta.ecommerce.domain.product.dto.ProductRequestDto;
import com.sparta.ecommerce.domain.product.dto.ProductResponseDto;
import com.sparta.ecommerce.domain.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    // 상품 등록
    @PostMapping
    public ResponseEntity<ProductResponseDto> registerProduct(@RequestBody ProductRequestDto productRequestDto) {
        ProductResponseDto productResponseDto = productService.registerProduct(ProductDto.fromRequest(productRequestDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(productResponseDto);
    }

    // 상품 조회
    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponseDto> retrieveProduct(@PathVariable Long productId) {
        ProductResponseDto productResponseDto = productService.retrieveProduct(productId);
        return ResponseEntity.ok(productResponseDto);
    }

    // 상품 수정
    @PutMapping("/{productId}")
    public ResponseEntity<ProductResponseDto> modifyProduct(@PathVariable Long productId, @RequestBody ProductRequestDto productRequestDto) {
        ProductResponseDto productResponseDto = productService.modifyProduct(productId, ProductDto.fromRequest(productRequestDto));
        return ResponseEntity.ok(productResponseDto);
    }

}
