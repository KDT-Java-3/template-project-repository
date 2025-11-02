package com.sparta.demo.repository;

import com.sparta.demo.domain.order.Order;
import com.sparta.demo.domain.order.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByUser_Id(Long userId);

    List<Order> findByUser_IdAndStatus(Long userId, OrderStatus status);

    // 단건 조회 - OrderItems + Product를 함께 조회
    @Query("SELECT o FROM Order o " +
           "JOIN FETCH o.orderItems oi " +
           "JOIN FETCH oi.product " +
           "WHERE o.id = :id")
    Optional<Order> findByIdWithItemsAndProducts(@Param("id") Long id);

    // 사용자별 주문 조회 - OrderItems + Product를 함께 조회
    @Query("SELECT DISTINCT o FROM Order o " +
           "JOIN FETCH o.orderItems oi " +
           "JOIN FETCH oi.product " +
           "WHERE o.user.id = :userId")
    List<Order> findByUserIdWithItemsAndProducts(@Param("userId") Long userId);

    // 사용자별 + 상태별 주문 조회 - OrderItems + Product를 함께 조회
    @Query("SELECT DISTINCT o FROM Order o " +
           "JOIN FETCH o.orderItems oi " +
           "JOIN FETCH oi.product " +
           "WHERE o.user.id = :userId AND o.status = :status")
    List<Order> findByUserIdAndStatusWithItemsAndProducts(
            @Param("userId") Long userId,
            @Param("status") OrderStatus status);
}
