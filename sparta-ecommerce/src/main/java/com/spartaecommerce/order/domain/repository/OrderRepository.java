package com.spartaecommerce.order.domain.repository;

import com.spartaecommerce.order.domain.entity.Order;
import com.spartaecommerce.order.domain.query.OrderSearchQuery;

import java.util.List;

public interface OrderRepository {

    Long save(Order order);

    List<Order> search(OrderSearchQuery searchQuery);
}
