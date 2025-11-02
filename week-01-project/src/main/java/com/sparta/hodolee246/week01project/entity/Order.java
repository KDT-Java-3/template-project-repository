package com.sparta.hodolee246.week01project.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sparta.hodolee246.week01project.service.request.OrderDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "orders")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private int quantity;

    @Column
    private String shippingAddress;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    public Order createOrder(Product product, User user, OrderDto orderDto) {
        int orderQuantity = orderDto.quantity();
        product.decreaseStock(orderQuantity);

        return Order.builder()
                .quantity(orderQuantity)
                .shippingAddress(orderDto.shippingAddress())
                .status(OrderStatus.COMPLETED)  // default status
                .product(product)
                .user(user)
                .build();
    }

    @Builder
    public Order(Long id, int quantity, String shippingAddress, OrderStatus status, User user, Product product) {
        this.id = id;
        this.quantity = quantity;
        this.shippingAddress = shippingAddress;
        this.status = status;
        this.user = user;
        this.product = product;
    }
}
