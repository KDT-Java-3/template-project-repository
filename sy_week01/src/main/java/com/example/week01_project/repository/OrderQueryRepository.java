package com.example.week01_project.repository;

import com.example.week01_project.domain.orders.Orders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface OrderQueryRepository {
    Page<Orders> search(Long userId, Orders.Status status, LocalDate from, LocalDate to, Pageable pageable);
}
