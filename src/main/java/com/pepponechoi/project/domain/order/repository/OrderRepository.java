package com.pepponechoi.project.domain.order.repository;

import com.pepponechoi.project.domain.order.entity.Order;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("SELECT o FROM Order o JOIN FETCH o.product WHERE o.user.id = :id")
    List<Order> findAllByUser_IdFetch(Long id);
}
