package com.spartaecommerce.product.domain.repository;

import com.spartaecommerce.product.domain.entity.Product;
import com.spartaecommerce.product.domain.query.ProductSearchQuery;

import java.util.List;

public interface ProductRepository {

    Long save(Product product);

    Product getById(Long productId);

    List<Product> search(ProductSearchQuery searchQuery);

}
