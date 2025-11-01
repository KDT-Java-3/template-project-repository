package com.sparta.work.order.controller;

import com.sparta.work.order.dto.response.ResponseOrderDto;
import com.sparta.work.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<List<ResponseOrderDto>> findAllOrder() {
        return ResponseEntity.ok(orderService.findAllOrder());
    }

}
