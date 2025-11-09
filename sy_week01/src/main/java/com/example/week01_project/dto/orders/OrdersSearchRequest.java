package com.example.week01_project.dto.orders;

import com.example.week01_project.domain.orders.Orders;

public record OrdersSearchRequest(Long userId, Orders.Status status, String from, String to) {}
