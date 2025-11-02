package com.example.demo.repository;

import com.example.demo.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // 카테고리별 상품 조회
    List<Product> findByCategoryId(Long categoryId);

    // 상품명으로 검색 (LIKE 검색)
    List<Product> findByNameContaining(String keyword);

    // 가격 범위로 검색
    List<Product> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);

    // 복합 검색: 카테고리 + 가격 범위 + 상품명
    @Query("SELECT p FROM Product p WHERE " +
           "(:categoryId IS NULL OR p.category.id = :categoryId) AND " +
           "(:minPrice IS NULL OR p.price >= :minPrice) AND " +
           "(:maxPrice IS NULL OR p.price <= :maxPrice) AND " +
           "(:keyword IS NULL OR p.name LIKE %:keyword%)")
    List<Product> searchProducts(
        @Param("categoryId") Long categoryId,
        @Param("minPrice") BigDecimal minPrice,
        @Param("maxPrice") BigDecimal maxPrice,
        @Param("keyword") String keyword
    );
}