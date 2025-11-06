package com.example.demo.service;

import com.example.demo.PurchaseStatus;
import com.example.demo.RefundStatus;
import com.example.demo.common.ServiceException;
import com.example.demo.common.ServiceExceptionCode;
import com.example.demo.controller.dto.RefundRequest;
import com.example.demo.controller.dto.RefundResponse;
import com.example.demo.entity.Product;
import com.example.demo.entity.Purchase;
import com.example.demo.entity.Refund;
import com.example.demo.repository.PurchaseRepository;
import com.example.demo.repository.RefundRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class RefundService {

    private final PurchaseRepository purchaseRepository;
    private final RefundRepository refundRepository;

    // [트랜잭션 실습] 환불 처리와 재고 복원을 하나의 트랜잭션으로 관리한다.
    @Transactional
    public RefundResponse processRefund(Long purchaseId, RefundRequest request) {
        Purchase purchase = purchaseRepository.findById(purchaseId)
                .orElseThrow(() -> new ServiceException(ServiceExceptionCode.NOT_FOUND_PURCHASE));

        if (purchase.getStatus() != PurchaseStatus.COMPLETED) {
            throw new ServiceException(ServiceExceptionCode.REFUND_NOT_ALLOWED);
        }

        String reason = request.getReason();
        if (!StringUtils.hasText(reason)) {
            throw new ServiceException(ServiceExceptionCode.REFUND_NOT_ALLOWED, "환불 사유가 필요합니다.");
        }

        Product product = purchase.getProduct();
        product.increaseStock(purchase.getQuantity());

        purchase.markRefunded();

        Refund refund = Refund.builder()
                .purchase(purchase)
                .reason(reason)
                .status(RefundStatus.APPROVED)
                .build();

        Refund savedRefund = refundRepository.save(refund);
        return RefundResponse.fromEntity(savedRefund);
    }
}
