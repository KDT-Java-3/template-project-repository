package com.example.week01_project.repository;

import com.example.week01_project.domain.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long>, ProductQueryRepository {
    boolean existsByName(String name);
}
