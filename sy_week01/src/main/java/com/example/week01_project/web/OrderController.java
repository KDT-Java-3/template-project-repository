package com.example.week01_project.web;

import com.example.week01_project.dto.orders.OrdersCreateRequest;
import com.example.week01_project.domain.orders.Orders;
import com.example.week01_project.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService service;
    public OrderController(OrderService service) { this.service = service; }

    @PostMapping
    public Long create(@RequestBody @Valid OrdersCreateRequest req){ return service.create(req); }

    @GetMapping("/{id}")
    public Orders get(@PathVariable Long id){ return service.get(id); }

    @GetMapping
    public Page<Orders> search(@RequestParam(required = false) Long userId,
                              @RequestParam(required = false) Orders.Status status,
                              @RequestParam(required = false) String from,
                              @RequestParam(required = false) String to,
                              Pageable pageable){
        return service.search(
                userId, status,
                from==null? null : LocalDate.parse(from),
                to==null? null : LocalDate.parse(to),
                pageable);
    }

    @PostMapping("/{id}/cancel")
    public void cancel(@PathVariable Long id){ service.cancel(id); }
}
