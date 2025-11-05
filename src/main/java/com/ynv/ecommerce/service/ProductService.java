package com.ynv.ecommerce.service;

import com.ynv.ecommerce.controller.dto.ProductRequest;
import com.ynv.ecommerce.entity.Category;
import com.ynv.ecommerce.entity.Product;
import com.ynv.ecommerce.repository.CategoryRepository;
import com.ynv.ecommerce.repository.ProductRepository;
import com.ynv.ecommerce.service.dto.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;


    // 1. 상품 등록
    public void createProduct(ProductRequest dto) {

        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("invalid category id"));

        Product product = Product.builder()
                .category(category)
                .name(dto.getName())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .stock(dto.getStock())
                .build();

        productRepository.save(product);

    }

    // 2. 단일 상품 상세 조회
    public ProductResponse getProduct(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("product not found"));

        return ProductResponse.fromEntity(product);

    }

}
