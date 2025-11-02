package com.spartaecommerce.order.presentation.controller;

import com.spartaecommerce.common.domain.CommonResponse;
import com.spartaecommerce.common.domain.IdResponse;
import com.spartaecommerce.common.domain.PageResponse;
import com.spartaecommerce.order.application.OrderService;
import com.spartaecommerce.order.application.dto.OrderInfo;
import com.spartaecommerce.order.domain.command.OrderCreateCommand;
import com.spartaecommerce.order.domain.command.OrderStatusUpdateCommand;
import com.spartaecommerce.order.domain.query.OrderSearchQuery;
import com.spartaecommerce.order.presentation.controller.dto.request.OrderCreateRequest;
import com.spartaecommerce.order.presentation.controller.dto.request.OrderSearchRequest;
import com.spartaecommerce.order.presentation.controller.dto.request.OrderStatusUpdateRequest;
import com.spartaecommerce.order.presentation.controller.dto.response.OrderResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/orders")
    public ResponseEntity<CommonResponse<IdResponse>> create(
        @Valid @RequestBody OrderCreateRequest createRequest
    ) {
        OrderCreateCommand createCommand = createRequest.toCommand();

        Long orderId = orderService.create(createCommand);

        return ResponseEntity
            .created(URI.create("/api/v1/orders/" + orderId))
            .body(CommonResponse.create(orderId));
    }

    @GetMapping("/orders")
    public ResponseEntity<CommonResponse<PageResponse<OrderResponse>>> getOrder(
        OrderSearchRequest searchRequest
    ) {
        OrderSearchQuery searchQuery = searchRequest.toQuery();
        List<OrderInfo> orders = orderService.search(searchQuery);
        List<OrderResponse> orderResponses = orders.stream()
            .map(OrderResponse::from)
            .toList();

        return ResponseEntity.ok(CommonResponse.success(PageResponse.of(orderResponses)));
    }

    @PatchMapping("/orders/{orderId}")
    public ResponseEntity<Void> updateOrderStatus(
        @PathVariable Long orderId,
        @Valid @RequestBody OrderStatusUpdateRequest updateRequest
    ) {
        OrderStatusUpdateCommand updateCommand = updateRequest.toCommand(orderId);

        orderService.updateOrderStatus(updateCommand);

        return ResponseEntity.noContent()
            .build();
    }

    @PostMapping("/orders/{orderId}/cancel")
    public ResponseEntity<Void> cancelOrder(@PathVariable Long orderId) {
        orderService.cancel(orderId);
        return ResponseEntity.noContent()
            .build();
    }
}
