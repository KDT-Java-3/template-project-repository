package com.example.week01_project.domain.refund;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "refund_items")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class RefundItem {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long refundId;

    @Column(nullable = false)
    private Long orderItemId;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal amount; // 환불 금액
}
