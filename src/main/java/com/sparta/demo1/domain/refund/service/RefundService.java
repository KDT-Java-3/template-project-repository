package com.sparta.demo1.domain.refund.service;

import com.sparta.demo1.common.error.CustomException;
import com.sparta.demo1.common.error.ExceptionCode;
import com.sparta.demo1.domain.product.dto.response.ProductResDto;
import com.sparta.demo1.domain.product.entity.ProductEntity;
import com.sparta.demo1.domain.product.repository.ProductRepository;
import com.sparta.demo1.domain.purchase.dto.mapper.PurchaseMapper;
import com.sparta.demo1.domain.purchase.dto.request.PurchaseReqDto;
import com.sparta.demo1.domain.purchase.dto.response.PurchaseResDto;
import com.sparta.demo1.domain.purchase.entity.PurchaseEntity;
import com.sparta.demo1.domain.purchase.entity.PurchaseProductEntity;
import com.sparta.demo1.domain.purchase.enums.PurchaseOrderBy;
import com.sparta.demo1.domain.purchase.enums.PurchaseStatus;
import com.sparta.demo1.domain.purchase.repository.PurchaseProductRepository;
import com.sparta.demo1.domain.purchase.repository.PurchaseQueryDsl;
import com.sparta.demo1.domain.purchase.repository.PurchaseRepository;
import com.sparta.demo1.domain.refund.dto.mapper.RefundMapper;
import com.sparta.demo1.domain.refund.dto.response.RefundResDto;
import com.sparta.demo1.domain.refund.entity.RefundEntity;
import com.sparta.demo1.domain.refund.enums.RefundStatus;
import com.sparta.demo1.domain.refund.repository.RefundQueryDsl;
import com.sparta.demo1.domain.refund.repository.RefundRepository;
import com.sparta.demo1.domain.user.entity.UserEntity;
import com.sparta.demo1.domain.user.enums.UserRate;
import com.sparta.demo1.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RefundService {

    private final RefundRepository refundRepository;
    private final PurchaseRepository purchaseRepository;
    private final UserRepository userRepository;

    private final RefundQueryDsl refundQueryDsl;

    private final RefundMapper refundMapper;

    @Transactional(readOnly = true)
    public Page<RefundResDto.RefundInfo> getRefundAdminFilter(RefundStatus refundStatus, Pageable pageable) {
        Page<RefundEntity> refundEntityList = refundQueryDsl.getRefundAdminFilter(refundStatus, pageable);

        return refundEntityList.map(refundMapper::toRes);
    }

    @Transactional(readOnly = true)
    public Page<RefundResDto.RefundInfo> getRefundFilter(Long userId, RefundStatus refundStatus, Pageable pageable) {
        Page<RefundEntity> refundEntityList = refundQueryDsl.getRefundFilter(userId, refundStatus, pageable);

        return refundEntityList.map(refundMapper::toRes);
    }

    @Transactional
    public Long createRefund(Long userId, Long purchaseId, String reason){
        PurchaseEntity purchaseEntity = purchaseRepository.findById(purchaseId)
                .orElseThrow(() -> new CustomException(ExceptionCode.NOT_EXIST));

        if(!purchaseEntity.getStatus().equals(PurchaseStatus.COMPLETED)){
            throw new CustomException(ExceptionCode.INTERNAL_SERVER_ERROR, "환불이 불가능한 상태입니다.");
        }

        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ExceptionCode.NOT_EXIST));

        return refundRepository.save(RefundEntity.builder()
                        .user(userEntity)
                        .purchase(purchaseEntity)
                        .reason(reason)
                        .status(RefundStatus.PENDING)
                .build()).getId();
    }

    @Transactional
    public void refundApprovedProcessing(Long userId, Long refundId){
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ExceptionCode.NOT_EXIST));

        if(!userEntity.getUserRate().equals(UserRate.MANAGER)){
            throw new CustomException(ExceptionCode.INTERNAL_SERVER_ERROR, "환불처리는 관리자만 가능합니다.");
        }

        RefundEntity refundEntity = refundQueryDsl.getRefundOfPurchaseAndProductById(refundId)
                .orElseThrow(() -> new CustomException(ExceptionCode.NOT_EXIST));

        // 1. 환불 상태 변경
        refundEntity.updateStatus(RefundStatus.APPROVED);

        // 2. 주문 상태 변경 (예: CANCELED or REFUNDED)
        PurchaseEntity purchaseEntity = refundEntity.getPurchase();
        purchaseEntity.updateStatus(PurchaseStatus.CANCELED);

        // 3. 상품 재고 복원
        purchaseEntity.getPurchaseProductList().forEach(purchaseProduct -> {
            ProductEntity product = purchaseProduct.getProduct();
            int restoreStock = product.getStock() + purchaseProduct.getQuantity();
            product.updateStock(restoreStock);
        });

    }

    @Transactional
    public void refundRejectedProcessing(Long userId, Long refundId){
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ExceptionCode.NOT_EXIST));

        if(!userEntity.getUserRate().equals(UserRate.MANAGER)){
            throw new CustomException(ExceptionCode.INTERNAL_SERVER_ERROR, "환불처리는 관리자만 가능합니다.");
        }

        RefundEntity refundEntity = refundRepository.findById(refundId)
                .orElseThrow(() -> new CustomException(ExceptionCode.NOT_EXIST));

        refundEntity.updateStatus(RefundStatus.REJECTED);
    }
}
