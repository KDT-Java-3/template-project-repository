package com.spartaecommerce.order.infrastructure.persistence.jpa.repository;

import com.spartaecommerce.common.exception.BusinessException;
import com.spartaecommerce.common.exception.ErrorCode;
import com.spartaecommerce.order.domain.entity.Order;
import com.spartaecommerce.order.domain.query.OrderSearchQuery;
import com.spartaecommerce.order.domain.repository.OrderRepository;
import com.spartaecommerce.order.infrastructure.persistence.jpa.entity.OrderJpaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepository {

    private final OrderJpaRepository orderJpaRepository;

    @Override
    public Long save(Order order) {
        OrderJpaEntity orderJpaEntity = OrderJpaEntity.from(order);
        return orderJpaRepository.save(orderJpaEntity).getOrderId();
    }

    @Override
    public List<Order> search(OrderSearchQuery searchQuery) {
        List<OrderJpaEntity> orderJpaEntities = orderJpaRepository.findAllByUserId(searchQuery.userId());
        return orderJpaEntities.stream()
            .map(OrderJpaEntity::toDomain)
            .toList();
    }

    @Override
    public Order getById(Long orderId) {
        OrderJpaEntity orderJpaEntity = orderJpaRepository.findById(orderId)
            .orElseThrow(() -> new BusinessException(ErrorCode.ENTITY_NOT_FOUND, "orderId: " + orderId));

        return orderJpaEntity.toDomain();
    }
}
