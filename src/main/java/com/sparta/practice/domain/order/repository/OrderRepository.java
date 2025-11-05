package com.sparta.practice.domain.order.repository;

import com.sparta.practice.domain.order.entity.Order;
import com.sparta.practice.domain.order.entity.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    // 사용자별 주문 조회
    List<Order> findByUserId(Long userId);

    // 사용자 + 주문 상태별 조회
    List<Order> findByUserIdAndStatus(Long userId, OrderStatus status);

    // 주문 상세 조회 (OrderItem 포함)
    @Query("SELECT o FROM Order o JOIN FETCH o.orderItems oi JOIN FETCH oi.product WHERE o.id = :orderId")
    Order findByIdWithItems(@Param("orderId") Long orderId);
}
