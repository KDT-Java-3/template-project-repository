package com.sparta.bootcamp.java_2_example.domain.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sparta.bootcamp.java_2_example.domain.product.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
