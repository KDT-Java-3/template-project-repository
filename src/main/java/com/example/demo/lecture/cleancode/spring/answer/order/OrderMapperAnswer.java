package com.example.demo.lecture.cleancode.spring.answer.order;

import com.example.demo.PurchaseStatus;
import com.example.demo.entity.Product;
import com.example.demo.entity.Purchase;
import com.example.demo.entity.User;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class OrderMapperAnswer {

    public Purchase toPurchase(User user, Product product, int quantity) {
        BigDecimal totalPrice = product.getPrice().multiply(BigDecimal.valueOf(quantity));
        return Purchase.builder()
                .user(user)
                .product(product)
                .quantity(quantity)
                .unitPrice(product.getPrice())
                .totalPrice(totalPrice)
                .status(PurchaseStatus.PENDING)
                .build();
    }

    public OrderResponse toResponse(Purchase purchase) {
        return new OrderResponse(
                purchase.getId(),
                purchase.getUser().getId(),
                purchase.getProduct().getId(),
                purchase.getQuantity(),
                purchase.getTotalPrice(),
                purchase.getStatus().name()
        );
    }
}
