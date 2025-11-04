package com.sparta.demo1.controller;

import com.sparta.demo1.domain.dto.request.OrderCreateRequest;
import com.sparta.demo1.domain.dto.request.OrderStatusUpdateRequest;
import com.sparta.demo1.domain.dto.response.OrderResponse;
import com.sparta.demo1.domain.entity.OrderStatus;
import com.sparta.demo1.domain.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Order", description = "주문 관리 API")
@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

  private final OrderService orderService;

  @Operation(summary = "주문 생성", description = "새로운 주문을 생성합니다.")
  @PostMapping
  public ResponseEntity<OrderResponse> createOrder(@Valid @RequestBody OrderCreateRequest request) {
    OrderResponse response = orderService.createOrder(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @Operation(summary = "주문 상세 조회", description = "특정 주문의 상세 정보를 조회합니다.")
  @GetMapping("/{orderId}")
  public ResponseEntity<OrderResponse> getOrder(@PathVariable Long orderId) {
    OrderResponse response = orderService.getOrder(orderId);
    return ResponseEntity.ok(response);
  }

  @Operation(summary = "사용자별 주문 목록 조회", description = "특정 사용자의 모든 주문을 조회합니다.")
  @GetMapping("/user/{userId}")
  public ResponseEntity<List<OrderResponse>> getOrdersByUser(@PathVariable Long userId) {
    List<OrderResponse> responses = orderService.getOrdersByUser(userId);
    return ResponseEntity.ok(responses);
  }

  @Operation(summary = "상태별 주문 목록 조회", description = "특정 상태의 모든 주문을 조회합니다.")
  @GetMapping("/status/{status}")
  public ResponseEntity<List<OrderResponse>> getOrdersByStatus(@PathVariable OrderStatus status) {
    List<OrderResponse> responses = orderService.getOrdersByStatus(status);
    return ResponseEntity.ok(responses);
  }

  @Operation(summary = "주문 상태 변경", description = "주문의 상태를 변경합니다.")
  @PatchMapping("/{orderId}/status")
  public ResponseEntity<OrderResponse> updateOrderStatus(
      @PathVariable Long orderId,
      @Valid @RequestBody OrderStatusUpdateRequest request) {
    OrderResponse response = orderService.updateOrderStatus(orderId, request);
    return ResponseEntity.ok(response);
  }

  @Operation(summary = "주문 취소", description = "대기 중인 주문을 취소하고 재고를 복구합니다.")
  @PostMapping("/{orderId}/cancel")
  public ResponseEntity<OrderResponse> cancelOrder(@PathVariable Long orderId) {
    OrderResponse response = orderService.cancelOrder(orderId);
    return ResponseEntity.ok(response);
  }
}