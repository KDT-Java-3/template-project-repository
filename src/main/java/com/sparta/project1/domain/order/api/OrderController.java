package com.sparta.project1.domain.order.api;

import com.sparta.project1.domain.PageResponse;
import com.sparta.project1.domain.order.api.dto.OrderRequest;
import com.sparta.project1.domain.order.api.dto.OrderResponse;
import com.sparta.project1.domain.order.service.OrderFindService;
import com.sparta.project1.domain.order.service.OrderModifyService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/orders")
public class OrderController {
    private final OrderModifyService orderModifyService;
    private final OrderFindService orderFindService;

    @PostMapping
    public ResponseEntity<Void> order(@Valid @RequestBody OrderRequest request) {
        orderModifyService.order(request);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<PageResponse<OrderResponse>> getOrderInfo(@PathVariable("userId") Long userId,
                                                                    @RequestParam("page") int page,
                                                                    @RequestParam("size") int size) {
        PageResponse<OrderResponse> orderResponses = orderFindService.getOrderInfo(userId, page - 1, size);

        return ResponseEntity.ok(orderResponses);
    }

    @PatchMapping("/status/{orderId}")
    public ResponseEntity<Void> changeStatus(@PathVariable("orderId") Long orderId,
                                             @Pattern (regexp = "PENDING|COMPLETED|CANCELED") @RequestParam String status) {
        orderModifyService.changeStatus(orderId, status);

        return ResponseEntity.ok().build();
    }

    // 주문 취소
    @DeleteMapping("/{userId}/{orderId}")
    public ResponseEntity<Void> cancelOrders(@PathVariable("userId") Long userId,
                                             @PathVariable("orderId") Long orderId) {
        orderModifyService.cancelOrder(userId, orderId);

        return ResponseEntity.noContent().build();
    }
}
