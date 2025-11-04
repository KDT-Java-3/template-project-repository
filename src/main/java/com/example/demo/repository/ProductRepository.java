package com.example.demo.repository;

import com.example.demo.entity.Category;
import com.example.demo.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    /**
     * 카테고리별 상품 조회
     * @param category 카테고리
     * @return 상품 목록
     */
    List<Product> findByCategory(Category category);

    /**
     * 카테고리 ID로 상품 조회
     * @param categoryId 카테고리 ID
     * @return 상품 목록
     */
    List<Product> findByCategoryId(Long categoryId);
}

