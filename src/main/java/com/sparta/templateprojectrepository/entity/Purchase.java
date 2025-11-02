package com.sparta.templateprojectrepository.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sparta.templateprojectrepository.PurchaseStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import java.awt.image.PixelGrabber;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Table
@Entity
@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="purchase_id")
    private Long id;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String shipping_address;

    @Column(nullable = false)
    BigDecimal totalPrice;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false,length = 20)
    PurchaseStatus status;

    @Column(nullable = false,updatable = false)
    @CreationTimestamp
    LocalDateTime purchasedAt;

    @Column(nullable = false)
    @UpdateTimestamp
    LocalDateTime updatedAt;


    @Builder
    public Purchase(Long id,
                    User user,
                    String shipping_address,
                    BigDecimal totalPrice,
                    PurchaseStatus status) {
        this.id = id;
        this.user = user;
        this.shipping_address = shipping_address;
        this.totalPrice = totalPrice;
        this.status = status;
    }

}
