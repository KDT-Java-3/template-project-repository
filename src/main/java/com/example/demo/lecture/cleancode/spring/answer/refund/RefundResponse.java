package com.example.demo.lecture.cleancode.spring.answer.refund;

public record RefundResponse(Long purchaseId, String status, String reason) {
}
