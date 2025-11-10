package com.example.demo.domain.category.repository;

import com.example.demo.domain.category.entity.Category;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    /**
     * 특정 부모 카테고리의 하위 카테고리 존재 여부 확인
     */
    boolean existsByParent_Id(Long parentId);

    /**
     * 루트 카테고리 조회 (parent가 null인 카테고리)
     */
    List<Category> findByParentIsNull();
}
