package com.example.demo.lecture.cleancode.spring.answer4;

import com.example.demo.PurchaseStatus;
import com.example.demo.entity.Product;
import com.example.demo.entity.Purchase;
import com.example.demo.entity.User;
import com.example.demo.repository.PurchaseRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class OrderCreationService {

    private final PurchaseRepository purchaseRepository;

    public OrderCreationService(PurchaseRepository purchaseRepository) {
        this.purchaseRepository = purchaseRepository;
    }

    public Purchase createOrder(User user, Product product, int quantity) {
        Purchase purchase = Purchase.builder()
                .user(user)
                .product(product)
                .quantity(quantity)
                .unitPrice(product.getPrice())
                .totalPrice(product.getPrice().multiply(BigDecimal.valueOf(quantity)))
                .status(PurchaseStatus.COMPLETED)
                .build();
        return purchaseRepository.save(purchase);
    }
}
