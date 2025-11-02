package com.sparta.heesue.repository;

import com.sparta.heesue.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // 카테고리로 조회
    List<Product> findByCategoryId(Long categoryId);

    // 상품명으로 검색 (부분 일치)
    List<Product> findByNameContaining(String keyword);

    // 가격 범위로 조회
    List<Product> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);

    // 카테고리 + 가격 범위
    List<Product> findByCategoryIdAndPriceBetween(Long categoryId, BigDecimal minPrice, BigDecimal maxPrice);

    // 카테고리 + 상품명 검색
    List<Product> findByCategoryIdAndNameContaining(Long categoryId, String keyword);
}