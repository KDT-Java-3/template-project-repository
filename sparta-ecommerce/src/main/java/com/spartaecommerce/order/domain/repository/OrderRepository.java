package com.spartaecommerce.order.domain.repository;

import com.spartaecommerce.order.domain.entity.Order;

public interface OrderRepository {

    Long save(Order order);
}
