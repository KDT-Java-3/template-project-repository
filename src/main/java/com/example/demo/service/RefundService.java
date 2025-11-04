package com.example.demo.service;

import com.example.demo.controller.dto.RefundProcessRequest;
import com.example.demo.controller.dto.RefundRequest;
import com.example.demo.controller.dto.RefundResponse;
import com.example.demo.entity.*;
import com.example.demo.repository.PurchaseRepository;
import com.example.demo.repository.RefundRepository;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RefundService {

    private final RefundRepository refundRepository;
    private final PurchaseRepository purchaseRepository;
    private final UserRepository userRepository;

    @Transactional
    public RefundResponse requestRefund(RefundRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        Purchase purchase = purchaseRepository.findById(request.getOrderId())
                .orElseThrow(() -> new IllegalArgumentException("주문을 찾을 수 없습니다."));

        // 주문 소유자 확인
        if (!purchase.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("본인의 주문만 환불 요청할 수 있습니다.");
        }

        Refund refund = Refund.builder()
                .purchase(purchase)
                .user(user)
                .reason(request.getReason())
                .status(Refund.RefundStatus.PENDING)
                .build();

        Refund savedRefund = refundRepository.save(refund);
        return toResponse(savedRefund);
    }

    @Transactional
    public RefundResponse processRefund(Long refundId, RefundProcessRequest request) {
        Refund refund = refundRepository.findById(refundId)
                .orElseThrow(() -> new IllegalArgumentException("환불 요청을 찾을 수 없습니다."));

        if (refund.getStatus() != Refund.RefundStatus.PENDING) {
            throw new IllegalArgumentException("처리 가능한 환불 요청은 PENDING 상태만 가능합니다.");
        }

        Refund.RefundStatus newStatus;
        try {
            newStatus = Refund.RefundStatus.valueOf(request.getStatus().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("유효하지 않은 환불 상태입니다: " + request.getStatus());
        }

        if (newStatus == Refund.RefundStatus.APPROVED) {
            refund.approve();
            // 환불 승인 시 재고 복원
            Purchase purchase = refund.getPurchase();
            for (PurchaseItem item : purchase.getPurchaseItems()) {
                item.getProduct().increaseStock(item.getQuantity());
            }
        } else if (newStatus == Refund.RefundStatus.REJECTED) {
            refund.reject();
        } else {
            throw new IllegalArgumentException("처리 가능한 상태는 APPROVED 또는 REJECTED입니다.");
        }

        Refund savedRefund = refundRepository.save(refund);
        return toResponse(savedRefund);
    }

    public List<RefundResponse> getUserRefunds(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        return refundRepository.findByUser(user).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public RefundResponse getRefund(Long refundId) {
        Refund refund = refundRepository.findById(refundId)
                .orElseThrow(() -> new IllegalArgumentException("환불 요청을 찾을 수 없습니다."));
        return toResponse(refund);
    }

    private RefundResponse toResponse(Refund refund) {
        return RefundResponse.builder()
                .id(refund.getId())
                .order(RefundResponse.OrderInfo.builder()
                        .id(refund.getPurchase().getId())
                        .totalPrice(refund.getPurchase().getTotalPrice())
                        .status(refund.getPurchase().getStatus().name())
                        .build())
                .user(RefundResponse.UserInfo.builder()
                        .id(refund.getUser().getId())
                        .name(refund.getUser().getName())
                        .email(refund.getUser().getEmail())
                        .build())
                .reason(refund.getReason())
                .status(refund.getStatus().name())
                .createdAt(refund.getCreatedAt())
                .updatedAt(refund.getUpdatedAt())
                .build();
    }
}

