package com.sparta.ecommerce.purchase.domain;

import com.sparta.ecommerce.common.domain.BaseEntity;
import com.sparta.ecommerce.product.domain.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Table(name = "purchase")
@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Purchase extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    @Comment("사용자 일련번호")
    private Long userId;

    @Comment("상품 일련번호")
    @OneToOne(fetch = FetchType.LAZY) // EAGER 대신 LAZY를 권장
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "quantity", nullable = false)
    @Comment("주문수량")
    private Integer quantity;

    @Enumerated(EnumType.STRING)
    @Comment("주문 상태")
    private PurchaseStatus status;

    @Column(name = "shipping_address", length = 1000, nullable = false)
    @Comment("배송 주소")
    private String shippingAddress;

    public void changeStatus(PurchaseStatus status, Product product) {

        if(this.status != PurchaseStatus.PENDING && status == PurchaseStatus.PENDING) {
            throw new IllegalArgumentException("Pending 상태로 변경은 불가능합니다.");
        }else if((status == PurchaseStatus.COMPLETED || status == PurchaseStatus.CANCELLED) && this.status != PurchaseStatus.PENDING) {
            throw new IllegalArgumentException("Pending 일때만 상태 변경이 가능합니다.");
        }

        this.status = status;

        if(status == PurchaseStatus.CANCELLED) {
            product.increaseStock(this.quantity);
        }
    }
}
