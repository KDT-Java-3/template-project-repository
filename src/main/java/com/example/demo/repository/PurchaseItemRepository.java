package com.example.demo.repository;

import com.example.demo.entity.Purchase;
import com.example.demo.entity.PurchaseItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseItemRepository extends JpaRepository<PurchaseItem, Long> {

    /**
     * 주문별 주문 상품 조회
     * @param purchase 주문
     * @return 주문 상품 목록
     */
    List<PurchaseItem> findByPurchase(Purchase purchase);

    /**
     * 주문 ID로 주문 상품 조회
     * @param purchaseId 주문 ID
     * @return 주문 상품 목록
     */
    List<PurchaseItem> findByPurchaseId(Long purchaseId);
}

