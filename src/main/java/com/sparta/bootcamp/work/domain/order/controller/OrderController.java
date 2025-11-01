package com.sparta.bootcamp.work.domain.order.controller;


import com.sparta.bootcamp.work.domain.order.dto.OrderDto;
import com.sparta.bootcamp.work.domain.order.dto.OrderEditRequest;
import com.sparta.bootcamp.work.domain.order.dto.OrderRequest;
import com.sparta.bootcamp.work.domain.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;


    @GetMapping("/order")
    public ResponseEntity<List<OrderDto>> editOrder(@RequestBody OrderRequest orderRequest) {
        return ResponseEntity.ok(orderService.getOrders(orderRequest) );
    }

    @PostMapping("/order")
    public ResponseEntity<String> createOrder(@RequestBody OrderRequest orderRequest) {
        Long id = orderService.createOrder(orderRequest);
        return ResponseEntity.ok("Order created with id: " + id);
    }

    @PutMapping("/order")
    public ResponseEntity<OrderDto> editOrder(@RequestBody OrderEditRequest orderEditRequest) {
        return ResponseEntity.ok(orderService.editOrder(orderEditRequest) );
    }

    @PutMapping("/order/cancel")
    public ResponseEntity<OrderDto> cancelOrder(@RequestBody OrderEditRequest orderEditRequest) {
        return ResponseEntity.ok(orderService.cancelOrder(orderEditRequest));
    }

}
