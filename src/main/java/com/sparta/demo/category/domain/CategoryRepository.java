package com.sparta.demo.category.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    default Category getById(Long id) {
        return findById(id).orElseThrow(() -> new IllegalArgumentException("카테고리를 찾을 수 없습니다."));
    }
}
