package com.example.stproject.domain.order.repository;

import com.example.stproject.domain.order.entity.Order;
import com.example.stproject.domain.order.type.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserIdOrderByIdDesc(Long userId);
    List<Order> findByUserIdAndStatusOrderByIdDesc(Long userId, OrderStatus status);
}
