package com.sparta.demo.controller;

import com.sparta.demo.common.ApiResponse;
import com.sparta.demo.controller.dto.order.OrderRequest;
import com.sparta.demo.controller.dto.order.OrderResponse;
import com.sparta.demo.controller.dto.order.OrderUpdateStatusRequest;
import com.sparta.demo.controller.mapper.OrderControllerMapper;
import com.sparta.demo.domain.order.OrderStatus;
import com.sparta.demo.service.OrderService;
import com.sparta.demo.service.dto.order.OrderDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Order", description = "주문 관리 API")
@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final OrderControllerMapper mapper;

    @Operation(summary = "주문 생성", description = "새로운 주문을 생성합니다.")
    @PostMapping
    public ResponseEntity<ApiResponse<OrderResponse>> createOrder(@Valid @RequestBody OrderRequest request) {
        var createDto = mapper.toCreateDto(request);
        OrderDto orderDto = orderService.createOrder(createDto);
        OrderResponse response = mapper.toResponse(orderDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(response));
    }

    @Operation(summary = "주문 단건 조회", description = "ID로 주문을 조회합니다.")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<OrderResponse>> getOrder(@PathVariable Long id) {
        OrderDto orderDto = orderService.getOrder(id);
        OrderResponse response = mapper.toResponse(orderDto);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @Operation(summary = "주문 목록 조회",
               description = "주문 목록을 조회합니다. Query Parameter로 필터링 가능: user-id, status")
    @GetMapping
    public ResponseEntity<ApiResponse<List<OrderResponse>>> getOrders(
            @RequestParam(name = "user-id", required = false) Long userId,
            @RequestParam(required = false) OrderStatus status) {
        List<OrderDto> orderDtos;

        if (userId != null && status != null) {
            orderDtos = orderService.getOrdersByUserIdAndStatus(userId, status);
        } else if (userId != null) {
            orderDtos = orderService.getOrdersByUserId(userId);
        } else {
            throw new IllegalArgumentException("user-id 파라미터는 필수입니다.");
        }

        List<OrderResponse> responses = orderDtos.stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success(responses));
    }

    @Operation(summary = "주문 상태 변경", description = "주문의 상태를 변경합니다.")
    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> updateOrderStatus(
            @PathVariable Long id,
            @Valid @RequestBody OrderUpdateStatusRequest request) {
        orderService.updateOrderStatus(id, request.getStatus());
        return ResponseEntity.ok(ApiResponse.success());
    }

    @Operation(summary = "주문 취소", description = "주문 취소를 요청합니다. (PENDING 상태만 가능)")
    @PostMapping("/{id}/cancellation")
    public ResponseEntity<ApiResponse<Void>> cancelOrder(@PathVariable Long id) {
        orderService.cancelOrder(id);
        return ResponseEntity.ok(ApiResponse.success());
    }
}
