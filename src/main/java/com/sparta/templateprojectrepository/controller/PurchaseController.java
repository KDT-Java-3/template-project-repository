package com.sparta.templateprojectrepository.controller;

import com.sparta.templateprojectrepository.dto.request.PurchaseCreateRequestDto;
import com.sparta.templateprojectrepository.entity.Purchase;
import com.sparta.templateprojectrepository.repository.PurchaseRepository;
import com.sparta.templateprojectrepository.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/purchase")
public class PurchaseController {
    @Autowired
    private PurchaseService purchaseService;

    //주문생성
    // 필수입력필드 user_id, product_id, quantity, shipping_address
    // 주문 생성 시 상품 재고 감소처리 필요
    @PostMapping("/order")
    public void createPurchase(@RequestBody PurchaseCreateRequestDto purchaseCreateRequestDto){
        purchaseService.createPurchase(purchaseCreateRequestDto);
    }

    //주문조회
    // 조회데이터 : 주문상태, 주문 날짜, 상품 상세
    @GetMapping("/order")
    public Optional<Purchase> getPurchase(@PathVariable Long id){
        return purchaseService.getPurchase(id);
    }

    //주문상태변경
    // 주문상태 변경 (status : pending -> completed/cancled)
    @PutMapping("/order")
    public void modifyPurchaseStatus(@RequestBody Purchase purchase){
        purchaseService.updateStatus(purchase);
    }

    //주문취소
    // pending일 경우에만 취소

}
