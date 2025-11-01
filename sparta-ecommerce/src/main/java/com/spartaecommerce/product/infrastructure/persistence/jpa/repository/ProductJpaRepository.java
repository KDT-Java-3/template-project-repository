package com.spartaecommerce.product.infrastructure.persistence.jpa.repository;

import com.spartaecommerce.product.infrastructure.persistence.jpa.entity.ProductJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductJpaRepository extends JpaRepository<ProductJpaEntity, Long> {
}
