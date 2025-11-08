package com.sprata.sparta_ecommerce.repository;

import com.sprata.sparta_ecommerce.entity.Order;

import java.util.List;

public interface OrderRepositoryCustom {
    List<Order> findByProductWhenComplete(Long productId);
}
