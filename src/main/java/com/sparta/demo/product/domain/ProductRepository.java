package com.sparta.demo.product.domain;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, Long> {

    default Product getById(Long id) {
        return findById(id)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));
    }

    @Query("""
           SELECT p FROM Product p WHERE
           (:categoryId IS NULL OR p.category.id = :categoryId) AND
           (:minPrice IS NULL OR p.price >= :minPrice) AND
           (:maxPrice IS NULL OR p.price <= :maxPrice) AND
           (:keyword IS NULL OR p.name LIKE %:keyword%)
           """)
    List<Product> findBySearchConditions(
            @Param("categoryId") Long categoryId,
            @Param("minPrice") BigDecimal minPrice,
            @Param("maxPrice") BigDecimal maxPrice,
            @Param("keyword") String keyword
    );
}
