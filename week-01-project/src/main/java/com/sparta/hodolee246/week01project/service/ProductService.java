package com.sparta.hodolee246.week01project.service;

import com.sparta.hodolee246.week01project.controller.request.ProductRequest;
import com.sparta.hodolee246.week01project.entity.Category;
import com.sparta.hodolee246.week01project.entity.Product;
import com.sparta.hodolee246.week01project.repository.CategoryRepository;
import com.sparta.hodolee246.week01project.repository.ProductRepository;
import com.sparta.hodolee246.week01project.service.request.ProductDto;
import com.sparta.hodolee246.week01project.service.response.ProductInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public ProductInfo addProduct(ProductDto request) {
        Category category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new RuntimeException("category not found"));

        Product product = Product.builder()
                .name(request.name())
                .price(request.price())
                .stock(request.stock())
                .category(category)
                .build();
        Product savedProduct = productRepository.save(product);

        return savedProduct.toResponse();
    }

    public List<ProductInfo> getProducts() {
        List<Product> all = productRepository.findAll();

        return all.stream()
                .map(Product::toResponse)
                .toList();
    }

    public ProductInfo getProduct(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("product not found"))
                .toResponse();
    }

    @Transactional
    public ProductInfo updateProduct(Long productId, ProductDto request) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("product not found"));
        Category category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new RuntimeException("category not found"));

        product.updateProduct(request, category);

        return product.toResponse();
    }
}
