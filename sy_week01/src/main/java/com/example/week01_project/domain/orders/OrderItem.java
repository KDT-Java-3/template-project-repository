package com.example.week01_project.domain.orders;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "order_items")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class OrderItem {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)  // 논리 FK
    private Long orderId;

    @Column(nullable = false)  // 논리 FK (원본 상품 참조용)
    private Long productId;

    // 스냅샷
    @Column(nullable = false, length = 200)
    private String productName;

    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal productPrice;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal subtotalAmount;
}
