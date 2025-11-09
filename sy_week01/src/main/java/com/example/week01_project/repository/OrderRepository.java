package com.example.week01_project.repository;

import com.example.week01_project.domain.orders.Orders;
import com.example.week01_project.domain.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Orders, Long>, OrderQueryRepository {
    long countByProductAndStatus(Product product, Orders.Status status);
}
