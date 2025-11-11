package com.example.demo.lecture.refactorsection1;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Spring MVC Controller 리팩토링 예제 (Before).
 * - Controller가 서비스/리포지토리까지 직접 의존해 단일 책임을 위반하는 모습을 보여준다.
 */
@RestController
@RequestMapping("/lecture/refactor-section1/orders")
public class RefactorSection1OrderControllerBefore {

    private final RefactorSection1OrderServiceBefore orderService;
    private final RefactorSection1OrderRepository orderRepository;

    public RefactorSection1OrderControllerBefore(RefactorSection1OrderServiceBefore orderService,
                                                 RefactorSection1OrderRepository orderRepository) {
        this.orderService = orderService;
        this.orderRepository = orderRepository;
    }

    @PostMapping
    public ResponseEntity<RefactorSection1OrderResponse> placeOrder(
            @Valid @RequestBody RefactorSection1OrderRequest request
    ) {
        RefactorSection1OrderResponse response = orderService.placeOrder(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<RefactorSection1OrderResponse> getOrder(@PathVariable Long orderId) {
        RefactorSection1Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));
        return ResponseEntity.ok(RefactorSection1OrderResponse.from(order));
    }
}
