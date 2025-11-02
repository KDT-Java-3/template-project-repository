package com.example.demo.domain.category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// Spring의 Repository Bean으로 등록 (생략 가능하지만 명시적으로 표현)
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    // ============================================
    // 커스텀 쿼리 메서드 (Query Method)
    // ============================================

    /**
     * name 존재 여부 확인
     * SELECT * FROM categories WHERE name = ?
     *
     * @param name 확인할 카테고리명
     * @return boolean - 존재하면 true, 없으면 false
     */
     boolean existsByName(String name);
}
