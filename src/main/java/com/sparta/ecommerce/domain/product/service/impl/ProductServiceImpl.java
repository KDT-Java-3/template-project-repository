package com.sparta.ecommerce.domain.product.service.impl;

import com.sparta.ecommerce.domain.product.dto.ProductDto;
import com.sparta.ecommerce.domain.product.dto.ProductRequestDto;
import com.sparta.ecommerce.domain.product.dto.ProductResponseDto;
import com.sparta.ecommerce.domain.product.entity.Product;
import com.sparta.ecommerce.domain.product.repository.ProductRepository;
import com.sparta.ecommerce.domain.product.service.ProductService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Override
    public ProductResponseDto registerProduct(ProductDto productDto) {

        Product savedProduct = productRepository.save(Product.fromDto(productDto));

        return ProductResponseDto.fromEntity(savedProduct);
    }

    @Override
    public ProductResponseDto retrieveProduct(Long productId) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));

        return ProductResponseDto.fromEntity(product);
    }

    @Override
    public ProductResponseDto modifyProduct(Long productId, ProductDto productDto) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));

        product.update(Product.fromDto(productDto));
        productRepository.save(product);

        return ProductResponseDto.fromEntity(product);
    }
}
