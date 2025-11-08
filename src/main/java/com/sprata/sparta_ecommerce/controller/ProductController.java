package com.sprata.sparta_ecommerce.controller;

import com.sprata.sparta_ecommerce.controller.mapper.ProductMapper;
import com.sprata.sparta_ecommerce.dto.param.PageDto;
import com.sprata.sparta_ecommerce.dto.ProductRequestDto;
import com.sprata.sparta_ecommerce.dto.ProductResponseDto;
import com.sprata.sparta_ecommerce.dto.param.SearchProductDto;
import com.sprata.sparta_ecommerce.service.ProductService;
import com.sprata.sparta_ecommerce.service.dto.ProductServiceInputDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import com.sprata.sparta_ecommerce.dto.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ProductMapper productMapper;

    @PostMapping
    public ResponseEntity<ResponseDto<?>> addProduct(@Valid @RequestBody ProductRequestDto productRequestDto) {
        ProductServiceInputDto service = productMapper.toService(productRequestDto);

        ProductResponseDto responseDto = productService.addProduct(productRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseDto.success(responseDto.getName(), "상품 추가 성공"));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ResponseDto<?>> getProduct(@PathVariable Long productId) {
        ProductResponseDto responseDto = productService.getProduct(productId);
        return ResponseEntity.ok(ResponseDto.success(responseDto, "상품 조회 성공"));
    }

    @GetMapping
    public ResponseEntity<ResponseDto<?>> getAllProducts(@ModelAttribute SearchProductDto searchDto,
                                                         @ModelAttribute PageDto pageDto) {

        List<ProductResponseDto> response = productService.getAllProducts(searchDto, pageDto);
        return ResponseEntity.ok(ResponseDto.success(response, "상품 목록 조회 성공"));
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ResponseDto<?>> updateProduct(@PathVariable Long productId,
                                                        @Valid @RequestBody ProductRequestDto productRequestDto) {
        ProductResponseDto responseDto = productService.updateProduct(productId, productRequestDto);
        return ResponseEntity.ok(ResponseDto.success(responseDto, "상품 수정 성공"));
    }
}
