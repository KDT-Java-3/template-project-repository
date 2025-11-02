package com.sparta.week1.order;

import com.sparta.week1.dto.order.CreateOrderDto;
import com.sparta.week1.entity.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("order")
public class OrderController {
    private final OrderService orderService;

    /**
     * 주문 생성
     */
    @PostMapping
    public ResponseEntity<Order> createOrder (@RequestBody CreateOrderDto dto) {
        Order order = orderService.createOrder(dto);
        return ResponseEntity.ok(order);
    }

    /**
     * 주문 조회
     */
    @GetMapping("/{userId}")
    public ResponseEntity<List<Order>> getOrderById (@RequestParam Long userId) {
        List<Order> order = this.orderService.getOrderById(userId);
        return ResponseEntity.ok().body(order);
    }

    /**
     * 주문 상태 변경
     */
    @PatchMapping("/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable Long id, @RequestBody String status) {
        Order order = this.orderService.updateOrderStatus(id, status);
        return ResponseEntity.ok().body(order);
    }

    /**
     * 주문 취소
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Order> deleteOrder(@PathVariable Long id) {
        Order order = this.orderService.deleteOrder(id);
        return ResponseEntity.ok().body(order);
    }
}
