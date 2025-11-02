package com.sparta.demo.repository;

import com.sparta.demo.domain.order.Order;
import com.sparta.demo.domain.order.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByUser_Id(Long userId);

    List<Order> findByUser_IdAndStatus(Long userId, OrderStatus status);
}
