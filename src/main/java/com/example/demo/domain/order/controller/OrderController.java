package com.example.demo.domain.order.controller;

import com.example.demo.domain.order.dto.request.OrderCreateRequest;
import com.example.demo.domain.order.dto.request.OrderSearchCondition;
import com.example.demo.domain.order.dto.response.OrderResponse;
import com.example.demo.domain.order.dto.response.OrderSummary;
import com.example.demo.domain.order.service.OrderService;
import com.example.demo.global.dto.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 주문 관리 API Controller
 */
@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    /**
     * 주문 생성
     */
    @PostMapping
    public ResponseEntity<ApiResponse<Long>> createOrder(@Valid @RequestBody OrderCreateRequest request) {
        Long orderId = orderService.createOrder(request);
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(ApiResponse.success(orderId));
    }

    /**
     * 주문 목록 조회 (동적 검색 + 페이징)
     */
    @GetMapping
    public ResponseEntity<ApiResponse<Page<OrderSummary>>> getOrders(
        OrderSearchCondition condition,
        @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<OrderSummary> orders = orderService.getOrders(condition, pageable);
        return ResponseEntity.ok(ApiResponse.success(orders));
    }

    /**
     * 주문 상세 조회
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<OrderResponse>> getOrderDetail(@PathVariable Long id) {
        OrderResponse order = orderService.getOrderDetail(id);
        return ResponseEntity.ok(ApiResponse.success(order));
    }

    /**
     * 주문 취소
     */
    @PostMapping("/{id}/cancel")
    public ResponseEntity<ApiResponse<Void>> cancelOrder(@PathVariable Long id) {
        orderService.cancelOrder(id);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    /**
     * 주문 완료 처리
     */
    @PutMapping("/{id}/complete")
    public ResponseEntity<ApiResponse<Void>> completeOrder(@PathVariable Long id) {
        orderService.completeOrder(id);
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}
