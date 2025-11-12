package com.example.demo.lecture.cleancode.spring.answer4;

public interface NotificationGateway {
    void notifyOrder(Long purchaseId, String userEmail, Long productId);
}
