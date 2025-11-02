package com.sparta.week01project.domain.product.service;

import com.sparta.week01project.domain.category.entity.Category;
import com.sparta.week01project.domain.category.repository.CategoryRepository;
import com.sparta.week01project.domain.product.dto.ProductDto;
import com.sparta.week01project.domain.product.dto.ProductMapper;
import com.sparta.week01project.domain.product.entity.Product;
import com.sparta.week01project.domain.product.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    @Transactional
    public ProductDto createProduct(ProductDto productDto) {

        Category category = categoryRepository.findById(productDto.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));

        Product product = Product.builder()
                .name(productDto.getName())
                .description(productDto.getDescription())
                .price(productDto.getPrice())
                .stock(productDto.getStock())
                .category(category)
                .build();
        Product saved = productRepository.save(product);

        return productMapper.toProductDto(saved);
    }
}
