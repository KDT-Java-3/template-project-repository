package com.example.demo.domain.purchaseproduct;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * PurchaseProduct Repository
 *
 * Spring Data JPA를 사용하여 purchase_products 테이블의 CRUD 작업 처리
 */
@Repository
public interface PurchaseProductRepository extends JpaRepository<PurchaseProduct, Long> {

    /**
     * 특정 구매(Purchase)에 속한 모든 구매-상품 내역 조회
     *
     * 자동 생성 쿼리:
     * SELECT * FROM purchase_products WHERE purchase_id = ?
     *
     * @param purchaseId 구매 ID
     * @return 해당 구매에 포함된 상품 목록
     */
    List<PurchaseProduct> findByPurchaseId(Long purchaseId);

    /**
     * 특정 상품(Product)이 포함된 모든 구매 내역 조회
     *
     * 자동 생성 쿼리:
     * SELECT * FROM purchase_products WHERE product_id = ?
     *
     * @param productId 상품 ID
     * @return 해당 상품이 포함된 구매 내역
     */
    List<PurchaseProduct> findByProductId(Long productId);
}