package com.spartaecommerce.product.infrastructure.persistence.jpa.repository;

import com.spartaecommerce.product.infrastructure.persistence.jpa.entity.ProductJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;

public interface ProductJpaRepository extends JpaRepository<ProductJpaEntity, Long> {

    List<ProductJpaEntity> findByCategoryIdAndPriceBetweenAndNameContaining(
        Long categoryId,
        BigDecimal minPrice,
        BigDecimal maxPrice,
        String keyword
    );
}
