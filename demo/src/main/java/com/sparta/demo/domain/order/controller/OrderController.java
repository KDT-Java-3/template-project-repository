package com.sparta.demo.domain.order.controller;

import com.sparta.demo.domain.order.dto.request.CreateOrderRequest;
import com.sparta.demo.domain.order.dto.request.UpdateOrderStatusRequest;
import com.sparta.demo.domain.order.dto.response.OrderResponse;
import com.sparta.demo.domain.order.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    // 등록
    @PostMapping
    public OrderResponse createOrder(@Valid @RequestBody CreateOrderRequest request) throws Exception {
        return orderService.createOrder(request);
    }

    // 조회
    @GetMapping("/{userId}")
    public List<OrderResponse> getByUserId(@PathVariable Long userId){
        return orderService.getOrderByUserId(userId);
    }

    // 수정
    @PutMapping("/{id}")
    public OrderResponse updateOrderStatus(@PathVariable Long id, @Valid @RequestBody UpdateOrderStatusRequest request) throws Exception {
        request.setId(id);
        return orderService.updateOrderStatus(request);
    }

    // 취소
    @DeleteMapping("/{id}")
    public OrderResponse cancelOrder(@PathVariable Long id) throws Exception {
        return orderService.cancelOrder(id);
    }

}
