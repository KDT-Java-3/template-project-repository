package com.sparta.ecommerce.domain.product.service;

import com.sparta.ecommerce.domain.product.dto.ProductDto;
import com.sparta.ecommerce.domain.product.dto.ProductResponseDto;

public interface ProductService {

    ProductResponseDto registerProduct(ProductDto productDto);

    ProductResponseDto retrieveProduct(Long productId);

    ProductResponseDto modifyProduct(Long productId, ProductDto productDto);

}
