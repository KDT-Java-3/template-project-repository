package com.example.week01_project.domain.orders;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "order_shipping")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class OrderShipping {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private Long orderId;

    @Column(nullable = false, length = 100)
    private String recipientName;

    private String phone;

    @Column(nullable = false, length = 255)
    private String addressLine1;

    private String addressLine2;
    private String city;
    private String state;
    private String postalCode;

    @Column(length = 2)
    private String country = "KR";
}

