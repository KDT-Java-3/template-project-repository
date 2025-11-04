package com.sparta.ecommerce.domain.order.controller;

import com.sparta.ecommerce.domain.order.dto.OrderCreateRequest;
import com.sparta.ecommerce.domain.order.dto.OrderResponse;
import com.sparta.ecommerce.domain.order.dto.OrderUpdateStateRequest;
import com.sparta.ecommerce.domain.order.dto.OrderUpsertDto;
import com.sparta.ecommerce.domain.order.entity.OrderStatus;
import com.sparta.ecommerce.domain.order.service.OrderService;
import java.util.List;
import lombok.Generated;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/api/orders"})
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderUpsertDto> createOrder(@RequestBody OrderCreateRequest dto) {
        OrderUpsertDto res = orderService.createOrder(dto);
        return ResponseEntity.ok(res);
    }

    @GetMapping({"/{id}"})
    public ResponseEntity<List<OrderResponse>> readOrdersByUser(@PathVariable Long id) {
        List<OrderResponse> res = orderService.readOrderByUser(id);
        return ResponseEntity.ok(res);
    }

    @PutMapping
    public ResponseEntity<OrderUpsertDto> updateOrderStatus(@RequestBody OrderUpdateStateRequest dto) {
        OrderUpsertDto res = orderService.updateState(dto);
        return ResponseEntity.ok(res);
    }

    @PutMapping({"/cancel"})
    public ResponseEntity<OrderUpsertDto> cancelOrder(@RequestBody Long id) {
        OrderUpdateStateRequest req = new OrderUpdateStateRequest();
        req.setId(id);
        req.setOrderStatus(OrderStatus.canceled);
        OrderUpsertDto res = orderService.updateState(req);
        return ResponseEntity.ok(res);
    }
}
