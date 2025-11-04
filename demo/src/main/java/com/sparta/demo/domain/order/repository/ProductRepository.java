package com.sparta.demo.domain.order.repository;

import com.sparta.demo.domain.order.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Product getProductByNameAndCategoryId(String name, Long categoryId);
}
