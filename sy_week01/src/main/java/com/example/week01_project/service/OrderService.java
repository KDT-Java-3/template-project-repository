package com.example.week01_project.service;

import com.example.week01_project.common.BadRequestException;
import com.example.week01_project.common.NotFoundException;
import com.example.week01_project.domain.orders.Orders;
import com.example.week01_project.domain.product.Product;
import com.example.week01_project.dto.orders.OrdersCreateRequest;
import com.example.week01_project.repository.OrderRepository;
import com.example.week01_project.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@Transactional(readOnly = true)
public class OrderService {
    private final OrderRepository orderRepo;
    private final ProductRepository productRepo;
    private final com.example.week01_project.repository.OrderQueryRepository orderQueryRepo;

    public OrderService(OrderRepository orderRepo, ProductRepository productRepo,
                        com.example.week01_project.repository.OrderQueryRepository orderQueryRepo) {
        this.orderRepo = orderRepo; this.productRepo = productRepo; this.orderQueryRepo = orderQueryRepo;
    }

    @Transactional
    public Long create(OrdersCreateRequest req){
        Product p = productRepo.findById(req.productId()).orElseThrow(() -> new NotFoundException("product not found"));
        if (p.getStock() < req.quantity()) throw new BadRequestException("insufficient stock");
        p.setStock(p.getStock() - req.quantity());

        Orders o = new Orders();
        o.setUserId(req.userId());
        o.setProduct(p);
        o.setQuantity(req.quantity());
        o.setShippingAddress(req.shippingAddress());
        // status 기본 PENDING
        orderRepo.save(o);
        return o.getId();
    }

    public Page<Orders> search(Long userId, Orders.Status status, LocalDate from, LocalDate to, Pageable pageable){
        return orderQueryRepo.search(userId, status, from, to, pageable);
    }

    public Orders get(Long id){
        return orderRepo.findById(id).orElseThrow(()-> new NotFoundException("order not found"));
    }

    @Transactional
    public void cancel(Long id){
        Orders o = orderRepo.findById(id).orElseThrow(()-> new NotFoundException("order not found"));
        if (o.getStatus() != Orders.Status.PENDING) throw new BadRequestException("only pending can be canceled");
        o.setStatus(Orders.Status.CANCELED);
        // 재고 복원
        Product p = o.getProduct();
        p.setStock(p.getStock() + o.getQuantity());
    }
}
