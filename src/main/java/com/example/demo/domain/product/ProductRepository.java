package com.example.demo.domain.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Spring의 Repository Bean으로 등록 (생략 가능하지만 명시적으로 표현)
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // ============================================
    // 커스텀 쿼리 메서드 (Query Method)
    // ============================================

    /**
     * name 존재 여부 확인
     * SELECT * FROM products WHERE name = ?
     *
     * @param name
     * @return boolean
     */
    boolean existsByName(String name);
}
