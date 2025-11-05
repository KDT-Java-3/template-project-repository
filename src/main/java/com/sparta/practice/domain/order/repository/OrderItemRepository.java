package com.sparta.practice.domain.order.repository;

import com.sparta.practice.domain.order.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    // 주문별 주문 아이템 조회
    List<OrderItem> findByOrderId(Long orderId);
}
