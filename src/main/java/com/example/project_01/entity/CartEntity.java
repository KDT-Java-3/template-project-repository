package com.example.project_01.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Check;

@Entity(name = "CART")
@NoArgsConstructor
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartEntity {

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

    @Column(name = "DEL_YN", nullable = false, length = 1)
    @Check(constraints = "DEL_YN IN ('Y', 'N')")
    String delYn;
}