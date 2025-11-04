package com.sparta.demo1.domain.repository;

import com.sparta.demo1.domain.entity.Category;
import com.sparta.demo1.domain.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

  // 카테고리별 조회
  List<Product> findByCategory(Category category);

  // 상품명 키워드 검색 (LIKE 검색)
  List<Product> findByNameContaining(String keyword);

  // 가격 범위 조회
  List<Product> findByPriceBetween(Long minPrice, Long maxPrice);

  // 카테고리 + 가격 범위
  List<Product> findByCategoryAndPriceBetween(Category category, Long minPrice, Long maxPrice);

  // 상품명 키워드 + 가격 범위
  List<Product> findByNameContainingAndPriceBetween(String keyword, Long minPrice, Long maxPrice);

  // 카테고리 + 상품명 키워드
  List<Product> findByCategoryAndNameContaining(Category category, String keyword);

  // 카테고리 + 상품명 키워드 + 가격 범위 (전체 조건)
  List<Product> findByCategoryAndNameContainingAndPriceBetween(
      Category category, String keyword, Long minPrice, Long maxPrice);
}
