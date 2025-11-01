package com.sparta.commerce.management.service;

import com.sparta.commerce.management.dto.request.purchase.PurchaseCreateRequest;
import com.sparta.commerce.management.dto.request.purchase.PurchaseProductRequest;
import com.sparta.commerce.management.dto.request.purchase.PurchaseUpdateRequest;
import com.sparta.commerce.management.dto.response.purchase.PurchaseResponse;
import com.sparta.commerce.management.entity.Product;
import com.sparta.commerce.management.entity.Purchase;
import com.sparta.commerce.management.entity.PurchaseProduct;
import com.sparta.commerce.management.entity.User;
import com.sparta.commerce.management.repository.ProductRepository;
import com.sparta.commerce.management.repository.PurchaseProductRepository;
import com.sparta.commerce.management.repository.PurchaseRepository;
import com.sparta.commerce.management.repository.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.UUID;


@Service
@Transactional
@AllArgsConstructor
public class PurchaseService {

    private final UserRepository userRepository;
    private final PurchaseRepository purchaseRepository;
    private final ProductRepository productRepository;
    private final PurchaseProductRepository purchaseProductRepository;

    //주문 생성
    public PurchaseResponse save(@Valid PurchaseCreateRequest request) {

        //주문자
        User user = userRepository.findById(request.getUserId()).orElseThrow(() -> new NotFoundException("사용자를 찾을수 없습니다."));

        PurchaseProduct purchaseProduct; //주문 상품
        Product product; //상품

        //전체 물건수 && 금액 개산
        long totalCount = 0L;
        BigDecimal totalPrice = BigDecimal.ZERO;

        for (PurchaseProductRequest ppr : request.getPurchaseProductRequests()) {

            product = productRepository.findById(ppr.getId()).orElseThrow(() -> new NotFoundException("상품을 찾을수 없습니다."));

            if (product != null) {
                if (product.getStock() < ppr.getQuantity()) throw new NotFoundException("상품 재고가 부족합니다.");

                //전체 물건수 && 금액 개산
                totalPrice.add(product.getPrice().multiply(BigDecimal.valueOf(ppr.getQuantity())));
                totalCount += ppr.getQuantity();
            }
        }

        //주문입력
        Purchase purchase = Purchase.builder()
                .user(user)
                .totalCount(totalCount)
                .totalPrice(totalPrice)
                .postCode("12345")
                .recipientAddress("서울특별서 강남구 강남대로45")
                .recipientName("홍길동")
                .recipientPhone("010-1234-5678")
                .req("요청사항 없습니다.")
                .build();

        Purchase savePurchase  = purchaseRepository.save(purchase);

        //재고 감소 시작
        for (PurchaseProductRequest ppr : request.getPurchaseProductRequests()) {
            product = productRepository.findById(ppr.getId()).orElseThrow(() -> new NotFoundException("상품을 찾을수 없습니다."));
            if (product != null) {
                purchaseProduct = PurchaseProduct.builder()
                            .purchase(savePurchase)
                            .product(product)
                            .quantity(ppr.getQuantity())
                            .price(product.getPrice())
                            .build();

                //주문상품T 저장
                PurchaseProduct savePurchaseProduct = purchaseProductRepository.save(purchaseProduct);

                //주문T 주문상품목록 저장
                savePurchase.addPurchaseProduct(savePurchaseProduct);
                
                //상품 재고 수정
                product.decreaseStock(savePurchaseProduct.getQuantity());
            }
        }

        return PurchaseResponse.getPurchase(savePurchase);
    }

    //단일 주문
    public PurchaseResponse findById(UUID id){
        return PurchaseResponse.getPurchase(Objects.requireNonNull(purchaseRepository.findById(id).orElse(null)));
    }

    //전체 주문
    public List<PurchaseResponse> findAll(){
        return PurchaseResponse.getPurchaseList(purchaseRepository.findAll());
    }

    //특정 사용자의 주문 목록
    public List<PurchaseResponse> findAllByUserId(UUID userId) {
        return PurchaseResponse.getPurchaseList(purchaseRepository.findAllByUserId(String.valueOf(userId)));
    }

    //주문 상태 수정
    public PurchaseResponse updateStatus(UUID id, @Valid PurchaseUpdateRequest request) {
        Purchase purchase = purchaseRepository.findById(id).orElseThrow(()-> new NotFoundException("주문을 찾을수 없습니다."));
        purchase.updateStatus(request.getStatus());

        if(purchase.getStatus().equals("CANCELED")) this.restoreStock(purchase);

        return PurchaseResponse.getPurchase(purchase);
    }

    //주문 취소
    public PurchaseResponse cancelPurchase(UUID id, @Valid PurchaseUpdateRequest request) {
        Purchase purchase = purchaseRepository.findById(id).orElseThrow(()-> new NotFoundException("주문을 찾을수 없습니다."));
        purchase.updateStatus(request.getStatus());
        this.restoreStock(purchase);
        return PurchaseResponse.getPurchase(purchase);
    }

    //재고 되돌리기
    private void restoreStock(Purchase purchase) {
        //주문에 엮여있는 모든 상품 재고 추가
        for (PurchaseProduct purchaseProduct : purchase.getPurchaseProducts()) {
            Product product = purchaseProduct.getProduct();
            product.increaseStock(purchaseProduct.getQuantity());
        }
    }
}
