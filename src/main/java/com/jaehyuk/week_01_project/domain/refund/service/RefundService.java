package com.jaehyuk.week_01_project.domain.refund.service;

import com.jaehyuk.week_01_project.domain.product.entity.Product;
import com.jaehyuk.week_01_project.domain.purchase.entity.Purchase;
import com.jaehyuk.week_01_project.domain.purchase.entity.PurchaseProduct;
import com.jaehyuk.week_01_project.domain.purchase.enums.PurchaseStatus;
import com.jaehyuk.week_01_project.domain.purchase.repository.PurchaseProductRepository;
import com.jaehyuk.week_01_project.domain.purchase.repository.PurchaseRepository;
import com.jaehyuk.week_01_project.domain.refund.dto.CreateRefundRequest;
import com.jaehyuk.week_01_project.domain.refund.dto.ProcessRefundRequest;
import com.jaehyuk.week_01_project.domain.refund.dto.RefundResponse;
import com.jaehyuk.week_01_project.domain.refund.entity.Refund;
import com.jaehyuk.week_01_project.domain.refund.enums.RefundStatus;
import com.jaehyuk.week_01_project.domain.refund.repository.RefundRepository;
import com.jaehyuk.week_01_project.domain.user.entity.User;
import com.jaehyuk.week_01_project.domain.user.repository.UserRepository;
import com.jaehyuk.week_01_project.exception.custom.InvalidRefundRequestException;
import com.jaehyuk.week_01_project.exception.custom.InvalidRefundStatusException;
import com.jaehyuk.week_01_project.exception.custom.PurchaseNotFoundException;
import com.jaehyuk.week_01_project.exception.custom.RefundNotFoundException;
import com.jaehyuk.week_01_project.exception.custom.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RefundService {
    private final RefundRepository refundRepository;
    private final PurchaseRepository purchaseRepository;
    private final PurchaseProductRepository purchaseProductRepository;
    private final UserRepository userRepository;

    /**
     * 특정 사용자의 환불 요청 목록을 조회합니다.
     *
     * @param userId 사용자 ID
     * @param status 환불 상태 (null이면 전체 조회)
     * @return 환불 요청 목록
     */
    public List<RefundResponse> getRefunds(Long userId, RefundStatus status) {
        // 1. 환불 목록 조회 (상태별 필터링)
        List<Refund> refunds;
        if (status != null) {
            refunds = refundRepository.findByUserIdAndStatus(userId, status);
            log.debug("사용자 환불 조회 - userId: {}, status: {}, count: {}", userId, status, refunds.size());
        } else {
            refunds = refundRepository.findByUserId(userId);
            log.debug("사용자 환불 조회 - userId: {}, 전체, count: {}", userId, refunds.size());
        }

        if (refunds.isEmpty()) {
            log.info("환불 조회 완료 - userId: {}, 결과: 0건", userId);
            return List.of();
        }

        // 2. RefundResponse 변환
        List<RefundResponse> responses = refunds.stream()
                .map(RefundResponse::from)
                .collect(Collectors.toList());

        log.info("환불 조회 완료 - userId: {}, status: {}, 결과: {}건",
                userId, status != null ? status : "전체", responses.size());

        return responses;
    }

    /**
     * 환불 요청을 생성하고 재고를 복원합니다.
     *
     * @param userId 사용자 ID (로그인한 사용자)
     * @param request 환불 요청 (purchaseId, reason)
     * @return 환불 요청 응답 (환불 ID 및 상세 정보)
     * @throws UserNotFoundException 사용자를 찾을 수 없을 때
     * @throws PurchaseNotFoundException 주문을 찾을 수 없을 때
     * @throws InvalidRefundRequestException PENDING 상태가 아닐 때
     */
    @Transactional
    public RefundResponse createRefund(Long userId, CreateRefundRequest request) {
        // 1. 사용자 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.warn("사용자를 찾을 수 없음 - userId: {}", userId);
                    return new UserNotFoundException("사용자를 찾을 수 없습니다. ID: " + userId);
                });

        // 2. 주문 조회
        Purchase purchase = purchaseRepository.findById(request.purchaseId())
                .orElseThrow(() -> {
                    log.warn("주문을 찾을 수 없음 - purchaseId: {}", request.purchaseId());
                    return new PurchaseNotFoundException("주문을 찾을 수 없습니다. ID: " + request.purchaseId());
                });

        // 3. 주문 소유자 확인
        if (!purchase.getUser().getId().equals(userId)) {
            log.warn("주문 소유자 불일치 - purchaseId: {}, userId: {}, ownerId: {}",
                    request.purchaseId(), userId, purchase.getUser().getId());
            throw new PurchaseNotFoundException("본인의 주문만 환불 요청할 수 있습니다.");
        }

        // 4. PENDING 상태 확인
        if (purchase.getStatus() != PurchaseStatus.PENDING) {
            log.warn("환불 요청 불가능 - purchaseId: {}, currentStatus: {}",
                    request.purchaseId(), purchase.getStatus());
            throw new InvalidRefundRequestException(
                    String.format("PENDING 상태의 주문만 환불 요청 가능합니다. 현재 상태: %s", purchase.getStatus())
            );
        }

        // 5. Refund 엔티티 생성
        Refund refund = Refund.builder()
                .purchase(purchase)
                .user(user)
                .reason(request.reason())
                .status(RefundStatus.REQUESTED)
                .build();

        Refund savedRefund = refundRepository.save(refund);

        // 6. 주문 상태를 REFUND_REQUESTED로 변경
        purchase.updateStatus(PurchaseStatus.REFUND_REQUESTED);

        log.info("환불 요청 완료 - refundId: {}, purchaseId: {}, userId: {}, reason: {}",
                savedRefund.getId(), request.purchaseId(), userId, request.reason());

        return RefundResponse.from(savedRefund);
    }

    /**
     * 환불 요청을 처리합니다 (승인 또는 거절).
     *
     * @param refundId 환불 요청 ID
     * @param request 처리 요청 (action: APPROVE/REJECT)
     * @return 처리된 환불 응답
     * @throws RefundNotFoundException 환불 요청을 찾을 수 없을 때
     * @throws InvalidRefundStatusException REQUESTED 상태가 아닐 때
     */
    @Transactional
    public RefundResponse processRefund(Long refundId, ProcessRefundRequest request) {
        // 1. 환불 요청 조회
        Refund refund = refundRepository.findById(refundId)
                .orElseThrow(() -> {
                    log.warn("환불 요청을 찾을 수 없음 - refundId: {}", refundId);
                    return new RefundNotFoundException("환불 요청을 찾을 수 없습니다. ID: " + refundId);
                });

        // 2. REQUESTED 상태 검증
        if (refund.getStatus() != RefundStatus.REQUESTED) {
            log.warn("환불 처리 불가능 - refundId: {}, currentStatus: {}",
                    refundId, refund.getStatus());
            throw new InvalidRefundStatusException(
                    String.format("REQUESTED 상태의 환불 요청만 처리 가능합니다. 현재 상태: %s", refund.getStatus())
            );
        }

        Purchase purchase = refund.getPurchase();

        if (request.action() == ProcessRefundRequest.RefundAction.APPROVE) {
            // 3-1. 승인 처리: 재고 복원 + 상태 변경
            List<PurchaseProduct> purchaseProducts = purchaseProductRepository
                    .findByPurchaseIdIn(List.of(purchase.getId()));

            for (PurchaseProduct purchaseProduct : purchaseProducts) {
                Product product = purchaseProduct.getProduct();
                product.increaseStock(purchaseProduct.getQuantity());

                log.debug("재고 복원 - productId: {}, quantity: {}, newStock: {}",
                        product.getId(), purchaseProduct.getQuantity(), product.getStock());
            }

            // Refund 상태를 APPROVED로 변경
            refund = Refund.builder()
                    .purchase(refund.getPurchase())
                    .user(refund.getUser())
                    .reason(refund.getReason())
                    .status(RefundStatus.APPROVED)
                    .build();
            refund = refundRepository.save(refund);

            // Purchase 상태를 REFUNDED로 변경
            purchase.updateStatus(PurchaseStatus.REFUNDED);

            log.info("환불 승인 완료 - refundId: {}, purchaseId: {}, 복원된 상품 수: {}",
                    refundId, purchase.getId(), purchaseProducts.size());

        } else {
            // 3-2. 거절 처리: 상태만 변경 (재고는 복원하지 않았으므로 차감도 불필요)
            // Refund 상태를 REJECTED로 변경
            refund = Refund.builder()
                    .purchase(refund.getPurchase())
                    .user(refund.getUser())
                    .reason(refund.getReason())
                    .status(RefundStatus.REJECTED)
                    .build();
            refund = refundRepository.save(refund);

            // Purchase 상태를 PENDING으로 되돌림
            purchase.updateStatus(PurchaseStatus.PENDING);

            log.info("환불 거절 완료 - refundId: {}, purchaseId: {}",
                    refundId, purchase.getId());
        }

        return RefundResponse.from(refund);
    }
}
