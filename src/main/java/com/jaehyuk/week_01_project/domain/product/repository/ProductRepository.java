package com.jaehyuk.week_01_project.domain.product.repository;

import com.jaehyuk.week_01_project.domain.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
