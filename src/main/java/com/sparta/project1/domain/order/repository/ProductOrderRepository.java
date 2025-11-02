package com.sparta.project1.domain.order.repository;

import com.sparta.project1.domain.order.domain.ProductOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductOrderRepository extends JpaRepository<ProductOrder, Long> {
}
