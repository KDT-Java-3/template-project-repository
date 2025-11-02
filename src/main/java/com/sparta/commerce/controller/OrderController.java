package com.sparta.commerce.controller;

import com.sparta.commerce.domain.order.dto.CreateOrderDto;
import com.sparta.commerce.domain.order.dto.OrderDto;
import com.sparta.commerce.facade.OrderFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderFacade orderFacade;

    @PostMapping
    public ResponseEntity<OrderDto> createOrder(
            @RequestBody @Valid CreateOrderDto dto
    ) {
        OrderDto order = orderFacade.createOrder(dto);
        return ResponseEntity.ok(order);
    }

}
