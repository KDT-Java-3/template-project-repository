package com.sparta.demo1.domain.order.repository;

import com.sparta.demo1.domain.order.entity.Order;
import com.sparta.demo1.domain.order.entity.OrderStatus;
import com.sparta.demo1.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

  // 전체 주문 조회 (최신순)
  List<Order> findAllByOrderByCreatedAtDesc();

  // 특정 사용자의 주문 목록 조회 (최신순)
  List<Order> findByUserOrderByCreatedAtDesc(User user);

  // 특정 사용자의 주문 목록 조회
  List<Order> findByUser(User user);

  // 특정 사용자의 특정 상태 주문 조회
  List<Order> findByUserAndOrderStatus(User user, OrderStatus status);

  // 주문 상태별 조회
  List<Order> findByOrderStatus(OrderStatus status);

  // 특정 사용자의 특정 상태 주문 조회 (최신순)
  List<Order> findByUserAndOrderStatusOrderByCreatedAtDesc(User user, OrderStatus status);
}
