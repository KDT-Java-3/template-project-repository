package com.sprata.sparta_ecommerce.service;

import com.sprata.sparta_ecommerce.dto.ProductResponseDto;
import com.sprata.sparta_ecommerce.dto.param.PageDto;
import com.sprata.sparta_ecommerce.dto.param.SearchProductDto;
import com.sprata.sparta_ecommerce.service.dto.ProductServiceInputDto;

import java.util.List;

public interface ProductService {
    ProductResponseDto addProduct(ProductServiceInputDto productRequestDto);

    ProductResponseDto getProduct(Long productId);

    List<ProductResponseDto> getAllProducts(SearchProductDto searchProductDto, PageDto pageDto);

    ProductResponseDto updateProduct(Long productId, ProductServiceInputDto productRequestDto);

    void deleteProduct(Long productId);
}