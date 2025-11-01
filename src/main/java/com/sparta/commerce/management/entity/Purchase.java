package com.sparta.commerce.management.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.*;
import org.webjars.NotFoundException;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
@Table(name = "purchase")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    User user;

    @NotNull
    @Column(name = "total_count", nullable = false)
    Long totalCount;

    @NotNull
    @Column(name = "total_price", nullable = false, precision = 10, scale = 2)
    BigDecimal totalPrice;

    @Size(max = 20)
    @ColumnDefault("'PENDING'")
    @Column(name = "status", length = 20)
    String status;

    @Size(max = 5)
    @NotNull
    @Column(name = "post_code", nullable = false, length = 6)
    String postCode;

    @Size(max = 255)
    @NotNull
    @Column(name = "recipient_address", nullable = false)
    String recipientAddress;

    @Size(max = 50)
    @NotNull
    @Column(name = "recipient_name", nullable = false, length = 50)
    String recipientName;

    @Size(max = 11)
    @NotNull
    @Column(name = "recipient_phone", nullable = false, length = 11)
    String recipientPhone;

    @Size(max = 100)
    @NotNull
    @Column(name = "req", nullable = false, length = 100)
    String req;

    @NotNull
    @CreationTimestamp
    @Column(name = "rg_dt", nullable = false)
    Instant rgDt;

    @NotNull
    @CreationTimestamp
    @UpdateTimestamp
    @Column(name = "ud_dt", nullable = false)
    Instant udDt;

    @OneToMany(mappedBy = "purchase")
    List<PurchaseProduct> purchaseProducts = new ArrayList<>();

    @Builder
    public Purchase(User user, Long totalCount, BigDecimal totalPrice, String postCode, String recipientAddress, String recipientName, String recipientPhone, String req) {
        this.user = user;
        this.totalCount = totalCount;
        this.totalPrice = totalPrice;
        this.postCode = postCode;
        this.recipientAddress = recipientAddress;
        this.recipientName = recipientName;
        this.recipientPhone = recipientPhone;
        this.req = req;
    }

    public void addPurchaseProduct(PurchaseProduct PurchaseProduct) {
        this.purchaseProducts.add(PurchaseProduct);
    }

    public void updateStatus(String status) {
        if (!"PENDING".equals(this.status)) throw new NotFoundException("보류 중인 주문만 변경 할수 있습니다.");

        this.status = status;
    }

}
