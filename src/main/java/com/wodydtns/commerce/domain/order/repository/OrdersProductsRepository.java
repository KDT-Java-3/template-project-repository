package com.wodydtns.commerce.domain.order.repository;

import com.wodydtns.commerce.domain.order.entity.OrdersProducts;
import com.wodydtns.commerce.global.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrdersProductsRepository extends JpaRepository<OrdersProducts, Long> {

    Optional<OrdersProducts> findByIdAndOrderStatus(Long id, OrderStatus orderStatus);

    void deleteByIdAndOrderStatus(Long id, OrderStatus orderStatus);

    @Query("SELECT op FROM OrdersProducts op JOIN op.order o JOIN FETCH op.product WHERE o.member.id = :memberId")
    List<OrdersProducts> findByMemberId(Long memberId);
}
