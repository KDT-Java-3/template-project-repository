package com.example.demo.domain.product.repository;

import com.example.demo.domain.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

    /**
     * 특정 카테고리에 속한 상품 존재 여부 확인
     */
    boolean existsByCategoryId(Long categoryId);
}
