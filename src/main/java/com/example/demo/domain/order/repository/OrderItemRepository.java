package com.example.demo.domain.order.repository;

import com.example.demo.domain.order.entity.OrderItem;
import com.example.demo.domain.order.entity.OrderStatus;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    /**
     * 주문 ID로 주문 항목 목록 조회
     */
    List<OrderItem> findByOrderId(Long orderId);

    /**
     * 상품 ID로 주문 항목 존재 여부 확인
     */
    boolean existsByProductId(Long productId);

    /**
     * 특정 상품이 특정 상태의 주문에 포함되어 있는지 확인
     * (Product 삭제 검증용)
     */
    @Query("SELECT CASE WHEN COUNT(oi) > 0 THEN true ELSE false END " +
           "FROM OrderItem oi " +
           "WHERE oi.productId = :productId " +
           "AND oi.order.status = :status")
    boolean existsByProductIdAndOrderStatus(
        @Param("productId") Long productId,
        @Param("status") OrderStatus status
    );
}
