package com.spartaecommerce.order.infrastructure.persistence.jpa.repository;

import com.spartaecommerce.order.infrastructure.persistence.jpa.entity.OrderJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderJpaRepository extends JpaRepository<OrderJpaEntity, Long> {
}
