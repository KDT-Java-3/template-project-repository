package com.spartaecommerce.order.infrastructure.persistence.jpa.repository;

import com.spartaecommerce.order.domain.entity.Order;
import com.spartaecommerce.order.domain.repository.OrderRepository;
import com.spartaecommerce.order.infrastructure.persistence.jpa.entity.OrderJpaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepository {

    private final OrderJpaRepository orderJpaRepository;

    @Override
    public Long save(Order order) {
        OrderJpaEntity orderJpaEntity = OrderJpaEntity.from(order);
        return orderJpaRepository.save(orderJpaEntity).getOrderId();
    }
}
