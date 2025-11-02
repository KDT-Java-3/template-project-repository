package com.example.project_01.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;


@Entity(name = "USER")
@Getter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SEQ", nullable = false, updatable = false)
    Integer seq;

    @Column(name = "USER_ID", nullable = false, unique = true, length = 50)
    String userId;

    @Column(name = "NAME", nullable = false, length = 30)
    String name;

    @Column(name = "SHIPPING_ADDRESS", nullable = false)
    String shippingAddress;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    List<CartEntity> carts;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    List<OrderEntity> orders;
}