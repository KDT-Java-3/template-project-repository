package com.sparta.demo.repository;

import com.sparta.demo.domain.order.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    // 특정 주문의 모든 주문 항목 조회
    List<OrderItem> findByOrderId(Long orderId);

    // 특정 상품이 포함된 모든 주문 항목 조회
    List<OrderItem> findByProductId(Long productId);
}
