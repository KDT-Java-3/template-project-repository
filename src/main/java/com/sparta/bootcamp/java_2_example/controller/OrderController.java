package com.sparta.bootcamp.java_2_example.controller;

import com.sparta.bootcamp.java_2_example.common.enums.OrderStatus;
import com.sparta.bootcamp.java_2_example.dto.request.OrderRequest;
import com.sparta.bootcamp.java_2_example.dto.response.OrderResponse;
import com.sparta.bootcamp.java_2_example.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    /**
     * 주문 생성
     */
    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@Valid @RequestBody OrderRequest request) {
        OrderResponse response = orderService.createOrder(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * 주문 상세 조회
     */
    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrder(@PathVariable Long id) {
        OrderResponse response = orderService.getOrder(id);
        return ResponseEntity.ok(response);
    }

    /**
     * 사용자별 주문 목록 조회
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderResponse>> getOrdersByUser(@PathVariable Long userId) {
        List<OrderResponse> responses = orderService.getOrdersByUser(userId);
        return ResponseEntity.ok(responses);
    }

    /**
     * 사용자별 주문 목록 조회 (상태별)
     */
    @GetMapping("/user/{userId}/status/{status}")
    public ResponseEntity<List<OrderResponse>> getOrdersByUserAndStatus(
            @PathVariable Long userId,
            @PathVariable OrderStatus status) {
        List<OrderResponse> responses = orderService.getOrdersByUserAndStatus(userId, status);
        return ResponseEntity.ok(responses);
    }

    /**
     * 주문 상태 변경
     */
    @PatchMapping("/{id}/status")
    public ResponseEntity<OrderResponse> updateOrderStatus(
            @PathVariable Long id,
            @RequestParam OrderStatus status) {
        OrderResponse response = orderService.updateOrderStatus(id, status);
        return ResponseEntity.ok(response);
    }

    /**
     * 주문 취소
     */
    @PostMapping("/{id}/cancel")
    public ResponseEntity<OrderResponse> cancelOrder(@PathVariable Long id) {
        OrderResponse response = orderService.cancelOrder(id);
        return ResponseEntity.ok(response);
    }
}
