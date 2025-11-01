package com.sparta.commerce.management.service;

import com.sparta.commerce.management.dto.request.refund.RefundCreateRequest;
import com.sparta.commerce.management.dto.request.refund.RefundUpdateRequest;
import com.sparta.commerce.management.dto.response.refund.RefundResponse;
import com.sparta.commerce.management.entity.Product;
import com.sparta.commerce.management.entity.Purchase;
import com.sparta.commerce.management.entity.PurchaseProduct;
import com.sparta.commerce.management.entity.Refund;
import com.sparta.commerce.management.repository.PurchaseRepository;
import com.sparta.commerce.management.repository.RefundRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
@Transactional
@AllArgsConstructor
public class RefundService {

    private final RefundRepository refundRepository;
    private final PurchaseRepository purchaseRepository;

    //환불 요청
    public RefundResponse save(RefundCreateRequest request) {

        Purchase purchase = purchaseRepository.findById(request.getPurchaseId()).orElseThrow( ()-> new IllegalArgumentException( "환불 가능한 주문이 없습니다."));

        Refund refund = Refund.builder()
                .purchase(purchase)
                .reason(request.getReason())
                .build();

        return RefundResponse.getRefundResponse(refundRepository.save(refund));
    }

    //환불 처리
    public RefundResponse update(RefundUpdateRequest request) {
        Refund refund = refundRepository.findById(request.getId()).orElseThrow( ()-> new IllegalArgumentException( "환불 요청 목록이 없습니다."));

        refund.updateStatus(request.getStatus());

        // 환불된 상품의 재고 복원
        if(refund.getStatus().equals("approved")){
            for (PurchaseProduct purchaseProduct : refund.getPurchase().getPurchaseProducts()) {
                Product product = purchaseProduct.getProduct();
                product.increaseStock(purchaseProduct.getQuantity());
            }
        }

        return RefundResponse.getRefundResponse(refund);
    }

    //특정 사용자의 환불 요청 목록을 조회
    public List<RefundResponse> findAllByUserId(UUID purchaseUserId){
        return RefundResponse.getRefundResponseList(refundRepository.findAllByPurchaseUserId(String.valueOf(purchaseUserId)));
    }
}
