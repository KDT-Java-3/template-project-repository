package com.sparta.week01project.domain.product.controller;

import com.sparta.week01project.domain.product.controller.mapper.ProductWebMapper;
import com.sparta.week01project.domain.product.controller.request.ProductCreateRequest;
import com.sparta.week01project.domain.product.controller.response.ProductResponse;
import com.sparta.week01project.domain.product.dto.ProductDto;
import com.sparta.week01project.domain.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;
    private final ProductWebMapper productWebMapper;

    @PostMapping
    public ProductResponse saveProduct(@Valid @RequestBody ProductCreateRequest request) {
        ProductDto product = productService.createProduct(productWebMapper.toProductDto(request));
        return productWebMapper.toProductResponse(product);
    }
}
