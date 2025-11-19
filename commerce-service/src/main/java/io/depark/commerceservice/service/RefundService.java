package io.depark.commerceservice.service;

import io.depark.commerceservice.common.ServiceException;
import io.depark.commerceservice.common.ServiceExceptionCode;
import io.depark.commerceservice.controller.dto.*;
import io.depark.commerceservice.entity.Purchase;
import io.depark.commerceservice.entity.Refund;
import io.depark.commerceservice.entity.User;
import io.depark.commerceservice.entity.enums.PurchaseStatus;
import io.depark.commerceservice.entity.enums.RefundStatus;
import io.depark.commerceservice.repository.PurchaseJpaRepository;
import io.depark.commerceservice.repository.RefundJpaRepository;
import io.depark.commerceservice.repository.RefundQueryRepository;
import io.depark.commerceservice.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RefundService {

    private final RefundJpaRepository refundJpaRepository;
    private final RefundQueryRepository refundQueryRepository;
    private final PurchaseJpaRepository purchaseJpaRepository;
    private final UserJpaRepository userJpaRepository;

    @Transactional(readOnly = true)
    public RefundDetailResponse getRefund(Long id) {
        refundJpaRepository.findById(id)
                .orElseThrow(() -> new ServiceException(ServiceExceptionCode.NOT_FOUND_REFUND));

        return RefundDetailResponse.of(
                refundQueryRepository.findRefundDetails(id)
        );
    }

    @Transactional(readOnly = true)
    public Page<RefundResponse> searchRefunds(RefundSearchCondition condition, Pageable pageable) {
        Page<Refund> refunds = refundQueryRepository.searchRefunds(condition, pageable);
        return refunds.map(RefundResponse::fromEntity);
    }

    @Transactional
    public RefundResponse create(RefundCreateRequest request) {
        // 환불 대상 주문 조회
        Purchase purchase = purchaseJpaRepository.findById(request.getPurchaseId())
                .orElseThrow(() -> new ServiceException(ServiceExceptionCode.NOT_FOUND_PURCHASE));

        // 완료된 주문만 환불 가능
        if (purchase.getStatus() != PurchaseStatus.COMPLETED) {
            throw new ServiceException(ServiceExceptionCode.NOT_ALLOWED_REFUND);
        }

        // 사용자 조회
        User user = userJpaRepository.findById(request.getUserId())
                .orElseThrow(() -> new ServiceException(ServiceExceptionCode.NOT_FOUND_USER));

        if (!user.equals(purchase.getUser())) {
            throw new ServiceException(ServiceExceptionCode.NOT_PURCHASE_OWNER);
        }

        return RefundResponse.fromEntity(
                refundJpaRepository.save(
                        Refund.builder()
                                .purchase(purchase)
                                .reason(request.getReason())
                                .status(RefundStatus.PENDING)
                                .build()
                )
        );
    }

    @Transactional
    public RefundResponse process(Long id, RefundProcessRequest request) {
        Refund refund = refundJpaRepository.findById(id)
                .orElseThrow(() -> new ServiceException(ServiceExceptionCode.NOT_FOUND_REFUND));

        if (request.getStatus() == RefundStatus.APPROVED) {
            refund.approve();
        } else if (request.getStatus() == RefundStatus.REJECTED) {
            refund.reject();
        } else {
            throw new ServiceException(ServiceExceptionCode.INVALID_REFUND_STATUS_CODE);
        }
        return RefundResponse.fromEntity(refund);
    }
}
