package com.sparta.project1.domain.order.repository;

import com.sparta.project1.domain.order.domain.Orders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface OrdersRepository extends JpaRepository<Orders, Long> {
    @Query("select o from Orders o where o.user.id = :userId")
    Page<Orders> findByUserId(Long userId, Pageable pageable);


    @Query("select o from Orders o where o.user.id = :userId and o.id = :orderId")
    Optional<Orders> findByOrderIdAndUserId(@Param("orderId") Long orderId, @Param("userId")Long userId);
}
