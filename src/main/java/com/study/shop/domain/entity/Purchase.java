package com.study.shop.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Table(name = "purchase")
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private BigDecimal totalPrice;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Status status;

    @Column(columnDefinition = "TEXT" , nullable = false)
    private String shippingAddress;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Builder
    public Purchase(String userId, BigDecimal totalPrice, Status status
    ) {
        this.userId = userId;
        this.totalPrice = totalPrice;
        this.status = status;
    }

    public enum Status {
        PENDING, COMPLETED, CANCELED
    }
}
