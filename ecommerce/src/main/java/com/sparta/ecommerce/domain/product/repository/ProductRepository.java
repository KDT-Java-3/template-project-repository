package com.sparta.ecommerce.domain.product.repository;

import com.sparta.ecommerce.domain.product.entity.Product;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("    SELECT p FROM Product p JOIN FETCH p.category\n    WHERE (:categoryId IS NULL OR p.category.id = :categoryId)\n      AND (:minPrice IS NULL OR p.price >= :minPrice)\n      AND (:maxPrice IS NULL OR p.price <= :maxPrice)\n      AND (:name IS NULL OR p.name LIKE CONCAT('%', :name, '%'))\n")
    List<Product> search(@Param("categoryId") Long categoryId, @Param("minPrice") BigDecimal minPrice, @Param("maxPrice") BigDecimal maxPrice, @Param("name") String name);
}
