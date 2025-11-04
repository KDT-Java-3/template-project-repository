package com.sparta.project.repository;

import com.sparta.project.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // 카테고리로 상품 조회
    List<Product> findByCategoryId(Long categoryId);

    // 상품명 검색 (LIKE)
    List<Product> findByNameContainingIgnoreCase(String keyword);

    // 가격 범위로 조회
    List<Product> findByPriceBetween(Double minPrice, Double maxPrice);

    // 복합 검색 (카테고리 + 키워드 + 가격 범위)
    @Query("SELECT p FROM Product p WHERE " +
            "(:categoryId IS NULL OR p.category.id = :categoryId) AND " +
            "(:keyword IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%'))) AND " +
            "(:minPrice IS NULL OR p.price >= :minPrice) AND " +
            "(:maxPrice IS NULL OR p.price <= :maxPrice)")
    List<Product> searchProducts(
            @Param("categoryId") Long categoryId,
            @Param("keyword") String keyword,
            @Param("minPrice") Double minPrice,
            @Param("maxPrice") Double maxPrice
    );

    // 상품 상세 조회 (카테고리 정보 포함)
    @Query("SELECT p FROM Product p LEFT JOIN FETCH p.category WHERE p.id = :id")
    Optional<Product> findByIdWithCategory(@Param("id") Long id);
}
