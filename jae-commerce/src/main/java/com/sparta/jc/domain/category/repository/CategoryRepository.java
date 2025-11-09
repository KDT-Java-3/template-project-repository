package com.sparta.jc.domain.category.repository;


import com.sparta.jc.domain.category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Category 레파지토리
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}