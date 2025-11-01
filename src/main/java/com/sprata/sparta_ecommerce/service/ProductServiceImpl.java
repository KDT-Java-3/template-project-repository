package com.sprata.sparta_ecommerce.service;

import com.sprata.sparta_ecommerce.controller.exception.DataNotFoundException;
import com.sprata.sparta_ecommerce.dto.ProductRequestDto;
import com.sprata.sparta_ecommerce.dto.ProductResponseDto;
import com.sprata.sparta_ecommerce.dto.param.PageDto;
import com.sprata.sparta_ecommerce.dto.param.SearchProductDto;
import com.sprata.sparta_ecommerce.entity.Category;
import com.sprata.sparta_ecommerce.entity.Product;
import com.sprata.sparta_ecommerce.repository.CategoryRepository;
import com.sprata.sparta_ecommerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    @Transactional
    public ProductResponseDto addProduct(ProductRequestDto productRequestDto) {
        Category category = categoryRepository.findById(productRequestDto.getCategory_id())
                .orElseThrow(() -> new DataNotFoundException("해당 카테고리를 찾을 수 없습니다."));

        Product product = Product.builder()
                .name(productRequestDto.getName())
                .description(productRequestDto.getDescription())
                .price(productRequestDto.getPrice())
                .stock(productRequestDto.getStock())
                .category(category)
                .build();

        Product savedProduct = productRepository.save(product);
        return new ProductResponseDto(savedProduct);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponseDto getProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new DataNotFoundException("해당 상품을 찾을 수 없습니다."));
        return new ProductResponseDto(product);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponseDto> getAllProducts(SearchProductDto searchProductDto, PageDto pageDto) {

        return productRepository.searchProducts(searchProductDto, pageDto).stream()
                .map(ProductResponseDto::new)
                .collect(Collectors.toList());
    }


    @Override
    @Transactional
    public ProductResponseDto updateProduct(Long productId, ProductRequestDto productRequestDto) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new DataNotFoundException("해당 상품을 찾을 수 없습니다."));

        Category category = categoryRepository.findById(productRequestDto.getCategory_id())
                .orElseThrow(() -> new DataNotFoundException("해당 카테고리를 찾을 수 없습니다."));

        product.update(productRequestDto.getName()
                ,productRequestDto.getDescription()
                ,productRequestDto.getPrice()
                ,productRequestDto.getStock()
                ,category
        );

        return new ProductResponseDto(product);
    }
}
