package com.sparta.project.domain.refund.service;

import com.sparta.project.common.enums.PurchaseStatus;
import com.sparta.project.common.enums.RefundStatus;
import com.sparta.project.domain.product.entity.Product;
import com.sparta.project.domain.purchase.entity.Purchase;
import com.sparta.project.domain.purchase.entity.PurchaseProduct;
import com.sparta.project.domain.purchase.repository.PurchaseRepository;
import com.sparta.project.domain.refund.dto.RefundCreateRequest;
import com.sparta.project.domain.refund.dto.RefundProcessRequest;
import com.sparta.project.domain.refund.dto.RefundResponse;
import com.sparta.project.domain.refund.entity.Refund;
import com.sparta.project.domain.refund.repository.RefundRepository;
import com.sparta.project.domain.user.entity.User;
import com.sparta.project.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RefundService {

    private final RefundRepository refundRepository;
    private final UserRepository userRepository;
    private final PurchaseRepository purchaseRepository;

    /**
     * 환불 요청
     */
    @Transactional
    public RefundResponse createRefund(RefundCreateRequest request) {
        // 사용자 조회
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다. ID: " + request.getUserId()));

        // 주문 조회
        Purchase purchase = purchaseRepository.findById(request.getPurchaseId())
                .orElseThrow(() -> new IllegalArgumentException("주문을 찾을 수 없습니다. ID: " + request.getPurchaseId()));

        // 환불 가능 여부 검증
        validateRefundRequest(user, purchase);

        // 환불 생성
        Refund refund = Refund.builder()
                .user(user)
                .purchase(purchase)
                .reason(request.getReason())
                .status(RefundStatus.PENDING)
                .build();

        Refund savedRefund = refundRepository.save(refund);

        return RefundResponse.from(savedRefund);
    }

    /**
     * 환불 요청 검증
     */
    private void validateRefundRequest(User user, Purchase purchase) {
        // 1. 주문이 해당 사용자의 것인지 확인
        if (!purchase.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("본인의 주문만 환불 요청할 수 있습니다.");
        }

        // 2. 완료된 주문만 환불 가능
        if (purchase.getStatus() != PurchaseStatus.COMPLETED) {
            throw new IllegalStateException("완료된 주문만 환불 요청할 수 있습니다. 현재 상태: " + purchase.getStatus());
        }

        // 3. 이미 환불 요청이 있는지 확인
        if (refundRepository.existsByPurchase(purchase)) {
            throw new IllegalStateException("이미 환불 요청이 존재하는 주문입니다.");
        }
    }

    /**
     * 환불 처리 (관리자)
     */
    @Transactional
    public RefundResponse processRefund(Long refundId, RefundProcessRequest request) {
        Refund refund = refundRepository.findById(refundId).orElseThrow();

        RefundStatus newStatus = request.getStatus();

        // 승인 또는 거절 처리
        if (newStatus == RefundStatus.APPROVED) {
            approveRefund(refund);
        } else if (newStatus == RefundStatus.REJECTED) {
            rejectRefund(refund, request.getRejectionReason());
        } else {
            throw new IllegalArgumentException("환불 상태는 APPROVED 또는 REJECTED만 가능합니다.");
        }

        return RefundResponse.from(refund);
    }

    private void approveRefund(Refund refund) {
        refund.approve();
        restoreStock(refund.getPurchase());
        refund.getPurchase().updateStatus(PurchaseStatus.CANCELED);
    }

    private void rejectRefund(Refund refund, String rejectionReason) {
        if (rejectionReason == null || rejectionReason.isBlank()) {
            throw new IllegalArgumentException("환불 거절 시 사유는 필수입니다.");
        }
        refund.reject(rejectionReason);
    }

    /**
     * 특정 사용자의 환불 목록 조회
     */
    public List<RefundResponse> getUserRefunds(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다. ID: " + userId));

        List<Refund> refunds = refundRepository.findByUserOrderByCreatedAtDesc(user);
        return RefundResponse.fromList(refunds);
    }

    /**
     * 재고 복구 (환불 승인 시)
     */
    private void restoreStock(Purchase purchase) {
        for (PurchaseProduct purchaseProduct : purchase.getPurchaseProducts()) {
            Product product = purchaseProduct.getProduct();
            product.increaseStock(purchaseProduct.getQuantity());
        }
    }
}