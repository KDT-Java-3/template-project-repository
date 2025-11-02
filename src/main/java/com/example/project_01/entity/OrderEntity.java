package com.example.project_01.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Check;

import java.time.LocalDateTime;

@Entity
@Table(name = "`ORDER`")  // 백틱으로 감싸기
@NoArgsConstructor
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SEQ", nullable = false, updatable = false)
    Integer seq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID", nullable = false)
    UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_ID", nullable = false)
    ProductEntity product;

    @Column(name = "QUANTITY", nullable = false)
    @Check(constraints = "QUANTITY >= 0")
    Integer quantity;

    @Column(name = "SHIPPING_ADDRESS", nullable = false, length = 255)
    String shippingAddress;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", nullable = false, length = 20)
    OrderStatus status;

    @Column(name = "ORDER_DATE", nullable = false)
    LocalDateTime orderDate;
}
