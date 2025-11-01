package com.sparta.ecommerce.domain.product.service.impl;

import com.sparta.ecommerce.domain.product.dto.ProductDto;
import com.sparta.ecommerce.domain.product.dto.ProductResponseDto;
import com.sparta.ecommerce.domain.product.entity.Product;
import com.sparta.ecommerce.domain.product.repository.ProductRepository;
import com.sparta.ecommerce.domain.product.service.ProductService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
