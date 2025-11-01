package com.sparta.ecommerce.domain.product.controller;

import com.sparta.ecommerce.domain.product.dto.ProductDto;
import com.sparta.ecommerce.domain.product.dto.ProductRequestDto;
import com.sparta.ecommerce.domain.product.dto.ProductResponseDto;
import com.sparta.ecommerce.domain.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
