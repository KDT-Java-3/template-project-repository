package com.sparta.hodolee246.week01project.repository;

import com.sparta.hodolee246.week01project.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
