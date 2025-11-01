package com.sparta.demo.order.controller;

import com.sparta.demo.order.controller.request.OrderSaveRequest;
import com.sparta.demo.order.controller.request.OrderStatusChangeRequest;
import com.sparta.demo.order.controller.response.OrderFindAllResponse;
import com.sparta.demo.order.domain.Order;
import com.sparta.demo.order.service.OrderService;
import com.sparta.demo.order.service.command.OrderSaveCommand;
import com.sparta.demo.order.service.command.OrderStatusChangeCommand;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/orders")
    public ResponseEntity<Void> save(@RequestBody OrderSaveRequest request) {
        OrderSaveCommand command = request.toCommand();
        Long id = orderService.save(command);
        return ResponseEntity.created(URI.create("/orders/" + id)).build();
    }

    @GetMapping("/users/{userId}/orders")
    public ResponseEntity<List<OrderFindAllResponse>> findByUserId(@PathVariable Long userId) {
        List<Order> orders = orderService.findAllByUserId(userId);
        List<OrderFindAllResponse> responses = orders.stream()
                .map(OrderFindAllResponse::of)
                .toList();
        return ResponseEntity.ok(responses);
    }

    @PatchMapping("/orders/{orderId}/status")
    public ResponseEntity<Void> changeStatus(
            @PathVariable Long orderId,
            @RequestBody OrderStatusChangeRequest request
    ) {
        OrderStatusChangeCommand command = request.toCommand(orderId);
        orderService.changeStatus(command);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/orders/{orderId}/cancel")
    public ResponseEntity<Void> cancel(@PathVariable Long orderId) {
        orderService.cancel(orderId);
        return ResponseEntity.noContent().build();
    }
}
