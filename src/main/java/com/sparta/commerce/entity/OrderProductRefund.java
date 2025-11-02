package com.sparta.commerce.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sparta.commerce.enums.RefundStatus;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Entity
public class OrderProductRefund extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonBackReference
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orderProduct_id", nullable = false)
    private OrderProduct order;

    private Integer quantity;

    @Enumerated(EnumType.STRING)
    private RefundStatus status;

    private BigDecimal price;

    private String reason;

}
