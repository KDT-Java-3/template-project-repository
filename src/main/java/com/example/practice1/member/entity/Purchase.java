package com.example.practice1.member.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.Instant;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@Table(name = "purchase")
@AllArgsConstructor(access = PRIVATE)
@NoArgsConstructor(access = PROTECTED)
public class Purchase {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotNull
    @Column(name = "total_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalPrice;

    @Size(max = 20)
    @ColumnDefault("'pending'")
    @Column(name = "status", length = 20)
    private String status;

    @Size(max = 6)
    @NotNull
    @Column(name = "ship_post_code", nullable = false, length = 6)
    private String shipPostCode;

    @Size(max = 255)
    @NotNull
    @Column(name = "ship_address", nullable = false)
    private String shipAddress;

    @Size(max = 255)
    @NotNull
    @Column(name = "ship_address_detail", nullable = false)
    private String shipAddressDetail;

    @Size(max = 50)
    @NotNull
    @Column(name = "ship_name", nullable = false, length = 50)
    private String shipName;

    @Size(max = 20)
    @NotNull
    @Column(name = "ship_phone_number", nullable = false, length = 20)
    private String shipPhoneNumber;

    @ColumnDefault("CURRENT_TIMESTAMP(6)")
    @Column(name = "created_at")
    private Instant createdAt;

    @ColumnDefault("CURRENT_TIMESTAMP(6)")
    @Column(name = "updated_at")
    private Instant updatedAt;

}