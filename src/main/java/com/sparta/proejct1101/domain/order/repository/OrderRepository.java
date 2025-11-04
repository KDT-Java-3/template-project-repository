package com.sparta.proejct1101.domain.order.repository;

import com.sparta.proejct1101.domain.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {

    @Query("SELECT o FROM Order o JOIN FETCH o.user JOIN FETCH o.product WHERE o.user.id = :userId")
    List<Order> findAllByUserIdWithDetails(@Param("userId") Long userId);

    @Query("SELECT o FROM Order o JOIN FETCH o.user JOIN FETCH o.product WHERE o.id = :id")
    Optional<Order> findByIdWithDetails(@Param("id") Long id);
}
