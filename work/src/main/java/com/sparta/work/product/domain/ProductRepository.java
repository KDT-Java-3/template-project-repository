package com.sparta.work.product.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT p FROM Product p JOIN FETCH p.category")
    List<Product> findAllWithCategory();

    @Query("SELECT p FROM Product p JOIN FETCH p.category")
    Optional<Product> findWithCategoryById(Long id);

    @Query("SELECT p FROM Product p JOIN FETCH p.category WHERE p.category.id = :categoryId")
    List<Product> findByCategoryId(Long categoryId);

    @Query("SELECT p FROM Product p JOIN FETCH p.category WHERE p.name LIKE %:name%")
    List<Product> findByNameContainingIgnoreCase(String name);
}
