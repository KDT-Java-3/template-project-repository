package com.example.week01_project.repository;

import com.example.week01_project.domain.orders.OrderShipping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderShippingRepository extends JpaRepository<OrderShipping, Long> {
    Optional<OrderShipping> findByOrderId(Long orderId);
}
