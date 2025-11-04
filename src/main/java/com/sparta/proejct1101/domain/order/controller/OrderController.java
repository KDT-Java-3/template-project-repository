package com.sparta.proejct1101.domain.order.controller;

import com.sparta.proejct1101.domain.order.dto.request.OrderReq;
import com.sparta.proejct1101.domain.order.dto.request.OrderStatusUpdateReq;
import com.sparta.proejct1101.domain.order.dto.response.OrderRes;
import com.sparta.proejct1101.domain.order.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderRes> createOrder(@Valid @RequestBody OrderReq req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createOrder(req));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderRes>> getOrdersByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(orderService.getOrdersByUserId(userId));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderRes> getOrder(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.getOrder(orderId));
    }

    @PatchMapping("/{orderId}/status")
    public ResponseEntity<OrderRes> updateOrderStatus(
            @PathVariable Long orderId,
            @Valid @RequestBody OrderStatusUpdateReq req) {
        return ResponseEntity.ok(orderService.updateOrderStatus(orderId, req));
    }
}
