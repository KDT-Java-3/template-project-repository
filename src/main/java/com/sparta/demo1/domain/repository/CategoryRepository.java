package com.sparta.demo1.domain.repository;

import com.sparta.demo1.domain.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

  // 카테고리 이름으로 찾기 (중복 체크용)
  Optional<Category> findByName(String name);

  // 최상위 카테고리 조회 (parent가 null인 것들)
  List<Category> findByParentIsNull();

  // 특정 부모 카테고리의 자식 카테고리들 조회
  List<Category> findByParent(Category parent);

  // 상품과 연관된 카테고리 조회 (상품이 있는 카테고리만)
  @Query("SELECT DISTINCT c FROM Category c JOIN c.products p")
  List<Category> findCategoriesWithProducts();
}
