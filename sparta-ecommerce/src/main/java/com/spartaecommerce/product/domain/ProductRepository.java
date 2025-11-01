package com.spartaecommerce.product.domain;

public interface ProductRepository {

    Long register(Product product);

    Product getProduct(Long productId);
}
