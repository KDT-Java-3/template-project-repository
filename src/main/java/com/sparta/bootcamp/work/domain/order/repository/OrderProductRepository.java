package com.sparta.bootcamp.work.domain.order.repository;

import com.sparta.bootcamp.work.domain.order.entity.Order;
import com.sparta.bootcamp.work.domain.order.entity.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {

    Optional<OrderProduct> findByOrder(Order order);

}
