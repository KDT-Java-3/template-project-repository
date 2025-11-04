package com.sparta.project.repository;

import com.sparta.project.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    // 이름으로 카테고리 존재 여부 확인
    boolean existsByName(String name);

    // ID로 카테고리 조회
    @Query("SELECT c FROM Category c LEFT JOIN FETCH c.products WHERE c.id = :id")
    Optional<Category> findByIdWithProducts(Long id);

    // 모든 카테고리 조회 (상품 정보 포함 - Fetch Join)
    @Query("SELECT DISTINCT c FROM Category c LEFT JOIN FETCH c.products")
    List<Category> findAllWithProducts();
}
