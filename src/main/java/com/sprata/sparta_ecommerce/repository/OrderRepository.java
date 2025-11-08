package com.sprata.sparta_ecommerce.repository;

import com.sprata.sparta_ecommerce.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long>, OrderRepositoryCustom {
    List<Order> findByUserId(Long userId);
}
