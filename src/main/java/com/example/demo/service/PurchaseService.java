package com.example.demo.service;

import com.example.demo.PurchaseStatus;
import com.example.demo.common.ServiceException;
import com.example.demo.common.ServiceExceptionCode;
import com.example.demo.controller.dto.PurchaseRequest;
import com.example.demo.controller.dto.PurchaseResponse;
import com.example.demo.entity.Product;
import com.example.demo.entity.Purchase;
import com.example.demo.entity.User;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.PurchaseRepository;
import com.example.demo.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class PurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final ProductRepository productRepository;
    private final UserJpaRepository userJpaRepository;

    // [트랜잭션 실습] 주문 생성 전체 흐름을 하나의 트랜잭션으로 묶는다.
    @Transactional
    public PurchaseResponse placePurchase(PurchaseRequest request) {
        User user = userJpaRepository.findById(request.getUserId())
                .orElseThrow(() -> new ServiceException(ServiceExceptionCode.NOT_FOUND_USER));

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ServiceException(ServiceExceptionCode.NOT_FOUND_PRODUCT));

        Integer requestedQuantity = request.getQuantity();
        if (requestedQuantity == null || requestedQuantity <= 0) {
            throw new ServiceException(ServiceExceptionCode.INVALID_ORDER_QUANTITY);
        }
        if (product.getStock() < requestedQuantity) {
            throw new ServiceException(ServiceExceptionCode.INSUFFICIENT_PRODUCT_STOCK);
        }

        product.decreaseStock(requestedQuantity);

        BigDecimal unitPrice = product.getPrice();
        BigDecimal totalPrice = unitPrice.multiply(BigDecimal.valueOf(requestedQuantity));

        Purchase purchase = Purchase.builder()
                .user(user)
                .product(product)
                .quantity(requestedQuantity)
                .unitPrice(unitPrice)
                .totalPrice(totalPrice)
                .status(PurchaseStatus.COMPLETED)
                .build();

        Purchase savedPurchase = purchaseRepository.save(purchase);
        return PurchaseResponse.fromEntity(savedPurchase);
    }
}
