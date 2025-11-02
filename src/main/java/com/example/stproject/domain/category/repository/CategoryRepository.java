package com.example.stproject.domain.category.repository;

import com.example.stproject.domain.category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByName(String name);

    @Query("""
    SELECT DISTINCT c
    FROM Category c
    LEFT JOIN FETCH c.products p
    LEFT JOIN FETCH p.category
    """)
    List<Category> findAllWithProducts();
}
