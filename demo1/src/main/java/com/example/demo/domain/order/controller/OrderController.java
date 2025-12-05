package com.example.demo.domain.order.controller;

import com.example.demo.domain.Status;
import com.example.demo.domain.order.dto.CreateOrderRequestDto;
import com.example.demo.domain.order.dto.OrderResponseDto;
import com.example.demo.domain.order.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponseDto> createOrder(@Valid @RequestBody CreateOrderRequestDto order){
        OrderResponseDto res = orderService.createOrder(order);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDto> getOrderById(@PathVariable Long id){
        OrderResponseDto response = orderService.getOrderById(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/complete")
    public ResponseEntity<Void> updateOrderStatusComplete(@PathVariable Long id){
        orderService.updateOrderStatusComplete(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<Void> updateOrderStatusCancel(@PathVariable Long id){
        orderService.updateOrderStatusCancel(id);
        return ResponseEntity.ok().build();
    }

}
