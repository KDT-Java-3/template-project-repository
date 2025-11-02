package com.sparta.demo.repository;

import com.sparta.demo.domain.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    // 최상위 카테고리만 조회 (parent가 null인 카테고리)
    List<Category> findByParentIsNull();

    // 특정 부모의 하위 카테고리 조회
    List<Category> findByParentId(Long parentId);

    // 이름으로 카테고리 찾기
    Optional<Category> findByName(String name);

    // 이름 중복 체크
    boolean existsByName(String name);
}
