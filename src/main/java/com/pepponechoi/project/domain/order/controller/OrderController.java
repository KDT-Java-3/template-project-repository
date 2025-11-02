package com.pepponechoi.project.domain.order.controller;

import com.pepponechoi.project.domain.order.dto.request.OrderChangeStatusRequest;
import com.pepponechoi.project.domain.order.dto.request.OrderCreateRequest;
import com.pepponechoi.project.domain.order.dto.request.OrderDeleteRequest;
import com.pepponechoi.project.domain.order.dto.response.OrderResponse;
import com.pepponechoi.project.domain.order.service.OrderService;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders/")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponse> create(@RequestBody OrderCreateRequest request) {
        OrderResponse response = orderService.create(request);
        return ResponseEntity.created(URI.create(String.format("/api/orders/%d", response.id()))).body(response);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<OrderResponse>> findAllByUser(@PathVariable Long userId) {
        List<OrderResponse> responses = orderService.findAllByUser(userId);
        return ResponseEntity.ok(responses);
    }

    @PutMapping
    public ResponseEntity<Boolean> changeStatus(@RequestBody OrderChangeStatusRequest request) {
        Boolean response = orderService.changeStatus(request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping
    public ResponseEntity<Boolean> delete(@RequestBody OrderDeleteRequest request) {
        Boolean response = orderService.delete(request);
        return ResponseEntity.ok(response);
    }
}
