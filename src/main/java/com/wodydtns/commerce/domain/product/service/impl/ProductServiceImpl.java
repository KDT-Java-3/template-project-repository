package com.wodydtns.commerce.domain.product.service.impl;

import com.wodydtns.commerce.domain.product.dto.CreateProductRequest;
import com.wodydtns.commerce.domain.product.dto.SearchProductRequest;
import com.wodydtns.commerce.domain.product.dto.SearchProductResponse;
import com.wodydtns.commerce.domain.product.dto.UpdateProductRequest;
import com.wodydtns.commerce.domain.product.entity.Product;
import com.wodydtns.commerce.domain.product.repository.ProductRepository;
import com.wodydtns.commerce.domain.product.service.ProductService;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    @Transactional
    @NonNull
    public Product createProduct(@NonNull CreateProductRequest createProductRequest) {
        Product product = Product.builder()
                .name(createProductRequest.getName())
                .price(createProductRequest.getPrice())
                .stock(createProductRequest.getStock())
                .categoryId(createProductRequest.getCategoryId())
                .description(createProductRequest.getDescription())
                .build();
        return productRepository.save(product);
    }

    @Override
    @Transactional
    @NonNull
    public Product updateProduct(@NonNull Long id, @NonNull UpdateProductRequest updateProductRequest) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("상품을 찾을 수 없습니다"));
        product = Product.builder()
                .id(product.getId())
                .name(updateProductRequest.getName())
                .price(updateProductRequest.getPrice())
                .stock(updateProductRequest.getStock())
                .categoryId(updateProductRequest.getCategoryId())
                .description(updateProductRequest.getDescription())
                .build();
        return productRepository.save(product);
    }

    @Override
    @NonNull
    public List<SearchProductResponse> searchProducts(@NonNull SearchProductRequest SearchProductRequest) {
        String name = SearchProductRequest.getName();
        Long categoryId = SearchProductRequest.getCategoryId();
        Integer minPrice = SearchProductRequest.getMinPrice();
        Integer maxPrice = SearchProductRequest.getMaxPrice();

        List<Product> products;

        if (name != null && categoryId == null && minPrice == null && maxPrice == null) {
            products = productRepository.findByNameContaining(name);
        } else if (categoryId != null && name == null && minPrice == null && maxPrice == null) {
            products = productRepository.findByCategoryId(categoryId);
        } else if (minPrice != null && maxPrice != null && name == null && categoryId == null) {
            products = productRepository.findByPriceBetween(minPrice, maxPrice);
        } else if (minPrice != null && maxPrice == null && name == null && categoryId == null) {
            products = productRepository.findByPriceGreaterThanEqual(minPrice);
        } else if (minPrice == null && maxPrice != null && name == null && categoryId == null) {
            products = productRepository.findByPriceLessThanEqual(maxPrice);
        } else if (name != null && categoryId != null && minPrice == null && maxPrice == null) {
            products = productRepository.findByNameContainingAndCategoryId(name, categoryId);
        } else if (name != null && minPrice != null && maxPrice != null && categoryId == null) {
            products = productRepository.findByNameContainingAndPriceBetween(name, minPrice, maxPrice);
        } else if (categoryId != null && minPrice != null && maxPrice != null && name == null) {
            products = productRepository.findByCategoryIdAndPriceBetween(categoryId, minPrice, maxPrice);
        } else if (name != null && categoryId != null && minPrice != null && maxPrice != null) {
            products = productRepository.findByNameContainingAndCategoryIdAndPriceBetween(name, categoryId, minPrice,
                    maxPrice);
        } else {
            products = productRepository.findAllWithCategory();
        }

        return products.stream()
                .map(product -> SearchProductResponse.builder()
                        .name(product.getName())
                        .price(product.getPrice())
                        .stock(product.getStock())
                        .description(product.getDescription())
                        .category(product.getCategory())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    @NonNull
    public Product findProduct(@NonNull Long id) {
        return productRepository.findByIdWithCategory(id)
                .orElseThrow(() -> new RuntimeException("상품을 찾을 수 없습니다"));
    }
}
