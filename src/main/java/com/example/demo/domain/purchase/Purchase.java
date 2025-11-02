package com.example.demo.domain.purchase;

import com.example.demo.domain.purchaseproduct.PurchaseProduct;
import com.example.demo.domain.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "purchases")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Purchase {

    // ===== Primary Key =====
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ===== Foreign Keys (관계 매핑) =====
    // 외래키 매핑 (ManyToOne 관계) - User와 N:1
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false,
                foreignKey = @ForeignKey(name = "fk_purchases_user"))
    private User userId;

    // ===== 일반 필드 =====
    @Column(nullable = false)
    private BigDecimal totalPrice;

    @Column(nullable = false)
    private String status; // pending, completed, canceled

    @Column(name = "shipping_address", length = 500)
    private String shippingAddress;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;


    // ==== 양방향 관계 - PurchaseProduct와 1:N ====
    // cascade: Purchase 저장/삭제 시 PurchaseProduct도 함께 처리
    // orphanRemoval: Purchase에서 제거된 PurchaseProduct는 DB에서도 삭제
    @OneToMany(mappedBy = "purchase", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<PurchaseProduct> purchaseProducts = new ArrayList<>();


    // ===== JPA 라이프사이클 콜백 =====
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

}
