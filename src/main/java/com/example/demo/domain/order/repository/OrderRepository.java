package com.example.demo.domain.order.repository;

import com.example.demo.domain.order.entity.Order;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

    /**
     * 사용자 ID로 주문 목록 조회
     */
    List<Order> findByUserId(Long userId);
}
