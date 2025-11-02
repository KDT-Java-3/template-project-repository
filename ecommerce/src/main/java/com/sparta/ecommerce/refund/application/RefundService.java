package com.sparta.ecommerce.refund.application;

import static com.sparta.ecommerce.refund.application.dto.RefundDto.*;

import com.sparta.ecommerce.product.domain.Product;
import com.sparta.ecommerce.product.infrastructure.ProductJpaRepository;
import com.sparta.ecommerce.purchase.domain.Purchase;
import com.sparta.ecommerce.purchase.domain.PurchaseStatus;
import com.sparta.ecommerce.purchase.infrastructure.PurchaseJpaRepository;
import com.sparta.ecommerce.refund.domain.Refund;
import com.sparta.ecommerce.refund.domain.RefundStatus;
import com.sparta.ecommerce.refund.infrastructure.RefundJpaRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RefundService {

    private final RefundJpaRepository refundJpaRepository;
    private final PurchaseJpaRepository purchaseJpaRepository;
    private final ProductJpaRepository productJpaRepository;

    @Transactional
    public RefundResponse createRefund(RefundCreateRequest createRequest) {
        // TODO userId 검증 추후에 필요
        Purchase purchase = purchaseJpaRepository.findById(createRequest.getPurchaseId())
                .orElseThrow(() -> new IllegalArgumentException("Purchase not found"));

        purchase.checkCompleted();

        Refund refund = createRequest.toEntity(purchase);
        return RefundResponse.fromEntity(refundJpaRepository.save(refund));
    }

    @Transactional
    public RefundResponse changeStatus(Long id, RefundStatus status) {
        Refund refund = refundJpaRepository.findByIdForUpdate(id)
                .orElseThrow(() -> new IllegalArgumentException("Refund not found"));

        Purchase purchase = purchaseJpaRepository.findByIdForUpdate(refund.getPurchase().getId())
                .orElseThrow(() -> new IllegalArgumentException("Purchase not found"));

        Product product = productJpaRepository.findByIdForUpdate(purchase.getProduct().getId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        refund.changeStatus(status);

        // 환불과 주문과의 취소 로직 충돌 발생
        if (status == RefundStatus.APPROVED) {
            purchase.changeStatus(PurchaseStatus.CANCELLED, product);
        }

        return RefundResponse.fromEntity(refund);
    }

    @Transactional(readOnly = true)
    public List<RefundResponse> getUserRefunds(Long userId) {
        return refundJpaRepository.findAllByUserIdAndStatus(userId, RefundStatus.PENDING)
                .stream().map(RefundResponse::fromEntity).toList();
    }
}
