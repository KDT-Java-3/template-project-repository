package com.example.week01_project.web;

import com.example.week01_project.common.ApiResponse;
import com.example.week01_project.domain.orders.Orders;
import com.example.week01_project.dto.orders.OrdersDtos.*;
import com.example.week01_project.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public ApiResponse<Resp> create(@RequestBody @Valid CreateReq req) {
        return ApiResponse.ok(orderService.create(req));
    }

    @GetMapping("/users/{userId}")
    public ApiResponse<List<Orders>> listByUser(@PathVariable Long userId) {
        return ApiResponse.ok(orderService.listByUser(userId));
    }

    @PatchMapping("/{orderId}/status")
    public ApiResponse<Resp> changeStatus(@PathVariable Long orderId, @RequestBody @Valid ChangeStatusReq req) {
        return ApiResponse.ok(orderService.changeStatus(orderId, req));
    }

    @PostMapping("/{orderId}/cancel")
    public ApiResponse<Resp> cancel(@PathVariable Long orderId, @RequestParam Long userId) {
        return ApiResponse.ok(orderService.cancel(orderId, userId));
    }
}
