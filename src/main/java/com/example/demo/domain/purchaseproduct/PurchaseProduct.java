package com.example.demo.domain.purchaseproduct;

import com.example.demo.domain.purchase.Purchase;
import com.example.demo.domain.product.Product;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * PurchaseProduct 엔티티
 *
 * 역할: Purchase(구매)와 Product(상품)의 중간 테이블 (다대다 관계 해소)
 * 테이블: purchase_products
 *
 * 특징:
 * - 한 구매(Purchase)에 여러 상품(Product) 포함 가능
 * - 한 상품(Product)이 여러 구매(Purchase)에 포함 가능
 * - 구매 당시의 수량(quantity)과 가격(price)을 별도로 저장
 */
@Entity
@Table(name = "purchase_products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseProduct {

    // ===== Primary Key =====
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ===== Foreign Keys (관계 매핑) =====
    /**
     * Purchase와의 N:1 관계
     * - 여러 PurchaseProduct가 하나의 Purchase에 속함
     * - LAZY 로딩: Purchase 정보가 필요할 때만 조회
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchase_id", nullable = false,
                foreignKey = @ForeignKey(name = "fk_purchase_products_purchase"))
    private Purchase purchase;

    /**
     * Product와의 N:1 관계
     * - 여러 PurchaseProduct가 하나의 Product를 참조
     * - LAZY 로딩: Product 정보가 필요할 때만 조회
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_purchase_products_product"))
    private Product product;

    // ===== 일반 필드 =====
    /**
     * 구매 수량
     * - 이 구매에서 해당 상품을 몇 개 샀는지
     */
    @Column(nullable = false)
    private Integer quantity;

    /**
     * 구매 당시 상품 가격
     * - Product의 현재 가격이 아닌, 구매 당시의 가격을 저장
     * - 가격 이력 보존 (상품 가격이 변경되어도 구매 내역은 유지)
     */
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    /**
     * 생성 일시
     * - 이 구매-상품 관계가 생성된 시점
     */
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;


    // ===== JPA 라이프사이클 콜백 =====
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}