package com.sparta.restful_1week.domain.order.controller;

import com.sparta.restful_1week.domain.order.dto.OrderInDTO;
import com.sparta.restful_1week.domain.order.dto.OrderOutDTO;
import com.sparta.restful_1week.domain.order.service.OrderService;
import com.sparta.restful_1week.domain.product.dto.ProductOutDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class OrderController {
    @PostMapping("/orders")
    public OrderOutDTO createOrder(@RequestBody OrderInDTO orderInDTO) {
        OrderService orderService = new OrderService();
        return orderService.createOrder(orderInDTO);
    }

    @GetMapping("/orders")
    public List<OrderOutDTO> getOrders() {
        OrderService orderService = new OrderService();
        return orderService.getOrders();
    }

    @PutMapping("/orders/{id}")
    public OrderOutDTO updateOrder(@PathVariable Long id, @RequestBody OrderInDTO orderInDTO) {
        OrderService orderService = new OrderService();
        return orderService.updateOrder(id, orderInDTO);
    }
}
