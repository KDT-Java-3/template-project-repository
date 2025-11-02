package com.sparta.demo1.repository;

import com.sparta.demo1.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Product, Integer> {


}
