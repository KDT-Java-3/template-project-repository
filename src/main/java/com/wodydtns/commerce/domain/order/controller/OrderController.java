package com.wodydtns.commerce.domain.order.controller;

import com.wodydtns.commerce.domain.order.dto.CreateOrderDto;
import com.wodydtns.commerce.domain.order.dto.CreateOrderRequest;
import com.wodydtns.commerce.domain.order.dto.OrdersProductsRequest;
import com.wodydtns.commerce.domain.order.dto.SearchOrdersProductsResponse;
import com.wodydtns.commerce.domain.order.service.OrderService;
import com.wodydtns.commerce.domain.order.service.OrdersProductsService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final OrdersProductsService ordersProductsService;

    @PostMapping
    public ResponseEntity<HttpStatus> createOrder(@RequestBody CreateOrderRequest createOrderRequest) {
        CreateOrderDto createOrderDto = CreateOrderDto.builder()
                .memberId(createOrderRequest.getUserId())
                .productIds(createOrderRequest.getProducts().stream()
                        .map(Integer::longValue)
                        .collect(Collectors.toList()))
                .quantity(createOrderRequest.getQuantity())
                .shippingAddress(createOrderRequest.getShippingAddress())
                .build();

        orderService.createOrder(createOrderDto);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<Void> updateOrderProduct(@PathVariable Long id, @RequestBody OrdersProductsRequest request) {
        ordersProductsService.updateOrdersProducts(id, request.getOrderStatus());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Void> deleteOrderProduct(@PathVariable Long id) {
        ordersProductsService.deleteOrdersProducts(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/products/{memberId}")
    public ResponseEntity<List<SearchOrdersProductsResponse>> searchOrdersProductsByMemberId(
            @PathVariable Long memberId) {
        List<SearchOrdersProductsResponse> ordersProductsList = ordersProductsService
                .searchOrdersProductsByMemberId(memberId);
        return ResponseEntity.ok(ordersProductsList);
    }

}
