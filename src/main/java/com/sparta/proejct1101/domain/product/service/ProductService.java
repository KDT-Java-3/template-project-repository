package com.sparta.proejct1101.domain.product.service;

import com.sparta.proejct1101.domain.product.dto.request.ProductReq;
import com.sparta.proejct1101.domain.product.dto.request.ProductSearchReq;
import com.sparta.proejct1101.domain.product.dto.response.ProductRes;

import java.util.List;

public interface ProductService {

    ProductRes saveProduct(ProductReq req);
    ProductRes updateProduct(Long id, ProductReq req);
    ProductRes getProduct(Long id);
    List<ProductRes> getProducts();
    List<ProductRes> searchProducts(ProductSearchReq searchReq);
}
