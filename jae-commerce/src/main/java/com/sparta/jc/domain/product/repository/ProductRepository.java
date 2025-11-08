package com.sparta.jc.domain.product.repository;

import com.sparta.jc.domain.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * 상품 CRUD 및 조건 검색용 Repository
 * JpaSpecificationExecutor → 검색/필터링 조건을 조합할 수 있도록 지원
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
}