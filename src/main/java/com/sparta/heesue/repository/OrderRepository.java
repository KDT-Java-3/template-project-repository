package com.sparta.heesue.repository;

import com.sparta.heesue.entity.Orders;
import com.sparta.heesue.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Orders, Long> {
    List<Orders> findByUser(User user);
}
