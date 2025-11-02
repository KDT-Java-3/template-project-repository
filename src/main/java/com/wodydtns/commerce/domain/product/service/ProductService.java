package com.wodydtns.commerce.domain.product.service;

import java.util.List;

import org.springframework.lang.NonNull;

import com.wodydtns.commerce.domain.product.dto.CreateProductRequest;
import com.wodydtns.commerce.domain.product.dto.SearchProductRequest;
import com.wodydtns.commerce.domain.product.dto.SearchProductResponse;
import com.wodydtns.commerce.domain.product.dto.UpdateProductRequest;
import com.wodydtns.commerce.domain.product.entity.Product;

public interface ProductService {

    @NonNull
    Product createProduct(@NonNull CreateProductRequest createProductRequest);

    @NonNull
    Product updateProduct(@NonNull Long id, @NonNull UpdateProductRequest updateProductRequest);

    @NonNull
    List<SearchProductResponse> searchProducts(@NonNull SearchProductRequest SearchProductRequest);

    @NonNull
    Product findProduct(@NonNull Long id);

}
