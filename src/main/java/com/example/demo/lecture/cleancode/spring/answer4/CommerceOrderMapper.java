package com.example.demo.lecture.cleancode.spring.answer4;

import com.example.demo.entity.Product;
import com.example.demo.entity.Purchase;
import com.example.demo.entity.User;
import org.springframework.stereotype.Component;

@Component
public class CommerceOrderMapper {

    public CommerceOrderResponse toResponse(Purchase purchase, User user, Product product) {
        return new CommerceOrderResponse(
                purchase.getId(),
                user.getId(),
                user.getEmail(),
                product.getId(),
                product.getName(),
                purchase.getQuantity(),
                purchase.getTotalPrice(),
                purchase.getStatus().name()
        );
    }
}
