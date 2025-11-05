package com.sparta.practice.domain.category.repository;

import com.sparta.practice.domain.category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    // 부모 카테고리로 조회 (하위 카테고리들)
    List<Category> findByParentId(Long parentId);

    // 최상위 카테고리 조회
    List<Category> findByParentIsNull();
}
