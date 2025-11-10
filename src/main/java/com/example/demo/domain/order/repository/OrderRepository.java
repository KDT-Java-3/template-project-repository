package com.example.demo.domain.order.repository;

import com.example.demo.domain.order.entity.Order;
import com.example.demo.domain.order.entity.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

    /**
     * 특정 상품의 특정 상태 주문 존재 여부 확인
     * (Product 삭제 검증용)
     */
    boolean existsByProductIdAndStatus(Long productId, OrderStatus status);
}
