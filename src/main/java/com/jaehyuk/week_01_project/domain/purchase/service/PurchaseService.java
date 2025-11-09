package com.jaehyuk.week_01_project.domain.purchase.service;

import com.jaehyuk.week_01_project.domain.product.entity.Product;
import com.jaehyuk.week_01_project.domain.product.repository.ProductRepository;
import com.jaehyuk.week_01_project.domain.purchase.dto.CreatePurchaseRequest;
import com.jaehyuk.week_01_project.domain.purchase.dto.PurchaseResponse;
import com.jaehyuk.week_01_project.domain.purchase.dto.UpdatePurchaseStatusRequest;
import com.jaehyuk.week_01_project.domain.purchase.entity.Purchase;
import com.jaehyuk.week_01_project.domain.purchase.entity.PurchaseProduct;
import com.jaehyuk.week_01_project.domain.purchase.enums.PurchaseStatus;
import com.jaehyuk.week_01_project.domain.purchase.repository.PurchaseProductRepository;
import com.jaehyuk.week_01_project.domain.purchase.repository.PurchaseRepository;
import com.jaehyuk.week_01_project.domain.user.entity.User;
import com.jaehyuk.week_01_project.domain.user.repository.UserRepository;
import com.jaehyuk.week_01_project.exception.custom.ProductNotFoundException;
import com.jaehyuk.week_01_project.exception.custom.PurchaseNotFoundException;
import com.jaehyuk.week_01_project.exception.custom.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PurchaseService {
    private final PurchaseRepository purchaseRepository;
    private final PurchaseProductRepository purchaseProductRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    /**
     * 특정 사용자의 주문 목록을 조회합니다.
     *
     * @param userId 사용자 ID
     * @param status 주문 상태 (null이면 전체 조회)
     * @return 주문 목록 (상품 정보 포함)
     */
    public List<PurchaseResponse> getPurchases(Long userId, PurchaseStatus status) {
        // 1. 주문 목록 조회 (상태별 필터링)
        List<Purchase> purchases;
        if (status != null) {
            purchases = purchaseRepository.findByUserIdAndStatus(userId, status);
            log.debug("사용자 주문 조회 - userId: {}, status: {}, count: {}", userId, status, purchases.size());
        } else {
            purchases = purchaseRepository.findByUserId(userId);
            log.debug("사용자 주문 조회 - userId: {}, 전체, count: {}", userId, purchases.size());
        }

        if (purchases.isEmpty()) {
            log.info("주문 조회 완료 - userId: {}, 결과: 0건", userId);
            return List.of();
        }

        // 2. 모든 주문의 상품 목록을 한 번에 조회 (N+1 문제 방지)
        List<Long> purchaseIds = purchases.stream()
                .map(Purchase::getId)
                .collect(Collectors.toList());

        List<PurchaseProduct> allPurchaseProducts = purchaseProductRepository.findByPurchaseIdIn(purchaseIds);

        // 3. 주문 ID별로 상품 그룹화
        Map<Long, List<PurchaseProduct>> purchaseProductMap = allPurchaseProducts.stream()
                .collect(Collectors.groupingBy(pp -> pp.getPurchase().getId()));

        // 4. PurchaseResponse 변환
        List<PurchaseResponse> responses = purchases.stream()
                .map(purchase -> PurchaseResponse.from(
                        purchase,
                        purchaseProductMap.getOrDefault(purchase.getId(), List.of())
                ))
                .collect(Collectors.toList());

        log.info("주문 조회 완료 - userId: {}, status: {}, 결과: {}건",
                userId, status != null ? status : "전체", responses.size());

        return responses;
    }

    /**
     * 주문을 생성합니다.
     *
     * @param userId 사용자 ID (로그인한 사용자)
     * @param request 주문 생성 요청 (shippingAddress, items)
     * @return 생성된 주문 ID
     * @throws UserNotFoundException 사용자를 찾을 수 없을 때
     * @throws ProductNotFoundException 상품을 찾을 수 없을 때
     * @throws com.jaehyuk.week_01_project.exception.custom.InsufficientStockException 재고가 부족할 때
     */
    @Transactional
    public Long createPurchase(Long userId, CreatePurchaseRequest request) {
        // 1. 사용자 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.warn("사용자를 찾을 수 없음 - userId: {}", userId);
                    return new UserNotFoundException("사용자를 찾을 수 없습니다. ID: " + userId);
                });

        // 2. 상품 조회 및 재고 확인/감소
        List<PurchaseProduct> purchaseProducts = new ArrayList<>();
        BigDecimal totalPrice = BigDecimal.ZERO;

        for (CreatePurchaseRequest.PurchaseItem item : request.items()) {
            // 상품 조회
            Product product = productRepository.findById(item.productId())
                    .orElseThrow(() -> {
                        log.warn("상품을 찾을 수 없음 - productId: {}", item.productId());
                        return new ProductNotFoundException("상품을 찾을 수 없습니다. ID: " + item.productId());
                    });

            // 재고 감소 (재고 부족 시 InsufficientStockException 발생)
            product.decreaseStock(item.quantity());

            // 가격 계산
            BigDecimal itemPrice = product.getPrice().multiply(BigDecimal.valueOf(item.quantity()));
            totalPrice = totalPrice.add(itemPrice);

            log.debug("주문 상품 처리 - productId: {}, quantity: {}, itemPrice: {}",
                    item.productId(), item.quantity(), itemPrice);
        }

        // 3. Purchase 생성 및 저장
        Purchase purchase = Purchase.builder()
                .user(user)
                .totalPrice(totalPrice)
                .status(PurchaseStatus.PENDING)
                .shippingAddress(request.shippingAddress())
                .build();

        Purchase savedPurchase = purchaseRepository.save(purchase);

        // 4. PurchaseProduct 생성 및 저장
        for (CreatePurchaseRequest.PurchaseItem item : request.items()) {
            Product product = productRepository.findById(item.productId()).get();

            PurchaseProduct purchaseProduct = PurchaseProduct.builder()
                    .purchase(savedPurchase)
                    .product(product)
                    .quantity(item.quantity())
                    .price(product.getPrice())
                    .build();

            purchaseProducts.add(purchaseProduct);
        }

        purchaseProductRepository.saveAll(purchaseProducts);

        log.info("주문 생성 완료 - purchaseId: {}, userId: {}, itemCount: {}, totalPrice: {}, shippingAddress: {}",
                savedPurchase.getId(), userId, request.items().size(), totalPrice, request.shippingAddress());

        return savedPurchase.getId();
    }

    /**
     * 주문 상태를 변경합니다.
     *
     * @param purchaseId 주문 ID
     * @param userId 사용자 ID (로그인한 사용자)
     * @param request 상태 변경 요청 (status)
     * @return 변경된 주문 ID
     * @throws PurchaseNotFoundException 주문을 찾을 수 없을 때
     * @throws com.jaehyuk.week_01_project.exception.custom.InvalidStatusTransitionException 상태 변경이 불가능할 때
     */
    @Transactional
    public Long updatePurchaseStatus(Long purchaseId, Long userId, UpdatePurchaseStatusRequest request) {
        // 1. 주문 조회
        Purchase purchase = purchaseRepository.findById(purchaseId)
                .orElseThrow(() -> {
                    log.warn("주문을 찾을 수 없음 - purchaseId: {}", purchaseId);
                    return new PurchaseNotFoundException("주문을 찾을 수 없습니다. ID: " + purchaseId);
                });

        // 2. 주문 소유자 확인
        if (!purchase.getUser().getId().equals(userId)) {
            log.warn("주문 소유자 불일치 - purchaseId: {}, userId: {}, ownerId: {}",
                    purchaseId, userId, purchase.getUser().getId());
            throw new PurchaseNotFoundException("본인의 주문만 수정할 수 있습니다.");
        }

        log.debug("주문 상태 변경 시도 - purchaseId: {}, currentStatus: {}, newStatus: {}",
                purchaseId, purchase.getStatus(), request.status());

        // 3. 상태 변경 (엔티티에서 검증)
        purchase.updateStatus(request.status());

        log.info("주문 상태 변경 완료 - purchaseId: {}, userId: {}, newStatus: {}",
                purchaseId, userId, request.status());

        return purchase.getId();
    }

    /**
     * 주문을 취소하고 재고를 복원합니다.
     *
     * @param purchaseId 주문 ID
     * @param userId 사용자 ID (로그인한 사용자)
     * @return 취소된 주문 ID
     * @throws PurchaseNotFoundException 주문을 찾을 수 없을 때
     * @throws com.jaehyuk.week_01_project.exception.custom.InvalidStatusTransitionException PENDING 상태가 아닐 때
     */
    @Transactional
    public Long cancelPurchase(Long purchaseId, Long userId) {
        // 1. 주문 조회
        Purchase purchase = purchaseRepository.findById(purchaseId)
                .orElseThrow(() -> {
                    log.warn("주문을 찾을 수 없음 - purchaseId: {}", purchaseId);
                    return new PurchaseNotFoundException("주문을 찾을 수 없습니다. ID: " + purchaseId);
                });

        // 2. 주문 소유자 확인
        if (!purchase.getUser().getId().equals(userId)) {
            log.warn("주문 소유자 불일치 - purchaseId: {}, userId: {}, ownerId: {}",
                    purchaseId, userId, purchase.getUser().getId());
            throw new PurchaseNotFoundException("본인의 주문만 취소할 수 있습니다.");
        }

        // 3. PENDING 상태 확인
        if (purchase.getStatus() != PurchaseStatus.PENDING) {
            log.warn("주문 취소 불가능 - purchaseId: {}, currentStatus: {}", purchaseId, purchase.getStatus());
            throw new com.jaehyuk.week_01_project.exception.custom.InvalidStatusTransitionException(
                    String.format("PENDING 상태의 주문만 취소 가능합니다. 현재 상태: %s", purchase.getStatus())
            );
        }

        // 4. 주문 상품 목록 조회 및 재고 복원
        List<PurchaseProduct> purchaseProducts = purchaseProductRepository.findByPurchaseIdIn(List.of(purchaseId));

        for (PurchaseProduct purchaseProduct : purchaseProducts) {
            Product product = purchaseProduct.getProduct();
            product.increaseStock(purchaseProduct.getQuantity());

            log.debug("재고 복원 - productId: {}, quantity: {}, newStock: {}",
                    product.getId(), purchaseProduct.getQuantity(), product.getStock());
        }

        // 5. 주문 상태를 CANCELED로 변경
        purchase.updateStatus(PurchaseStatus.CANCELED);

        log.info("주문 취소 완료 - purchaseId: {}, userId: {}, 복원된 상품 수: {}",
                purchaseId, userId, purchaseProducts.size());

        return purchase.getId();
    }
}
