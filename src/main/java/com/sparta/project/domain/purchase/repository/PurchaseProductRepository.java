package com.sparta.project.domain.purchase.repository;

import com.sparta.project.domain.purchase.entity.Purchase;
import com.sparta.project.domain.purchase.entity.PurchaseProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseProductRepository extends JpaRepository<PurchaseProduct, Long> {

    // 특정 주문의 모든 상품 조회
    List<PurchaseProduct> findByPurchase(Purchase purchase);

    // 특정 주문의 상품 개수 조회
    long countByPurchase(Purchase purchase);
}