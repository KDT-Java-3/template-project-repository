package com.sprata.sparta_ecommerce.service;

import com.sprata.sparta_ecommerce.dto.ProductRequestDto;
import com.sprata.sparta_ecommerce.dto.ProductResponseDto;
import com.sprata.sparta_ecommerce.dto.param.PageDto;
import com.sprata.sparta_ecommerce.dto.param.SearchProductDto;

import java.util.List;

public interface ProductService {
    ProductResponseDto addProduct(ProductRequestDto productRequestDto);

    ProductResponseDto getProduct(Long productId);

    List<ProductResponseDto> getAllProducts(SearchProductDto searchProductDto, PageDto pageDto);

    ProductResponseDto updateProduct(Long productId, ProductRequestDto productRequestDto);
}