package com.jaehyuk.week_01_project.domain.purchase.repository;

import com.jaehyuk.week_01_project.domain.purchase.entity.PurchaseProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseProductRepository extends JpaRepository<PurchaseProduct, Long> {
    /**
     * 특정 주문의 모든 상품 조회
     */
    List<PurchaseProduct> findByPurchaseId(Long purchaseId);

    /**
     * 여러 주문의 모든 상품 조회 (N+1 방지용)
     */
    List<PurchaseProduct> findByPurchaseIdIn(List<Long> purchaseIds);
}
