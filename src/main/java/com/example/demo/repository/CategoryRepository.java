package com.example.demo.repository;

import com.example.demo.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    /**
     * 부모 카테고리가 없는 최상위 카테고리 조회
     * @return 최상위 카테고리 목록
     */
    List<Category> findByParentIsNull();

    /**
     * 특정 부모 카테고리의 자식 카테고리 조회
     * @param parent 부모 카테고리
     * @return 자식 카테고리 목록
     */
    List<Category> findByParent(Category parent);
}

