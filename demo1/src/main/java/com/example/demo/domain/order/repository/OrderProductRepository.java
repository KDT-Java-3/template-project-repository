package com.example.demo.domain.order.repository;

import com.example.demo.domain.order.entity.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {

    List<OrderProduct> findByOrderId(Long orderId);

    @Query("SELECT op FROM OrderProduct op " +
            "JOIN FETCH op.product p " +
            "WHERE op.order.id = :orderId")
    List<OrderProduct> findByOrderIdWithProduct(@Param("orderId") Long orderId);
}
