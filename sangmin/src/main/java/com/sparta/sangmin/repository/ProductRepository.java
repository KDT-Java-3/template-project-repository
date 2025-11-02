package com.sparta.sangmin.repository;

import com.sparta.sangmin.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    // 카테고리별 조회
    List<Product> findByCategoryId(Long categoryId);

    // 가격 범위로 조회
    List<Product> findByPriceBetween(Integer minPrice, Integer maxPrice);

    // 상품명 키워드 검색
    List<Product> findByNameContaining(String keyword);

    // 복합 검색: 카테고리 + 가격 범위 + 키워드
    @Query("SELECT p FROM Product p WHERE " +
            "(:categoryId IS NULL OR p.category.id = :categoryId) AND " +
            "(:minPrice IS NULL OR p.price >= :minPrice) AND " +
            "(:maxPrice IS NULL OR p.price <= :maxPrice) AND " +
            "(:keyword IS NULL OR p.name LIKE %:keyword%)")
    List<Product> searchProducts(
            @Param("categoryId") Long categoryId,
            @Param("minPrice") Integer minPrice,
            @Param("maxPrice") Integer maxPrice,
            @Param("keyword") String keyword
    );
}
