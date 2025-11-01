package com.sparta.commerce.management.entity;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.*;
import org.webjars.NotFoundException;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
@Table(name = "refund")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Refund {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @OneToOne
    @JoinColumn(name = "purchase_id", nullable = false)
    Purchase purchase;

    @Lob
    @Column(name = "reason")
    String reason;

    @Size(max = 20)
    @ColumnDefault("'PENDING'")
    @Column(name = "status", length = 20)
    String status;

    @NotNull
    @CreationTimestamp
    @Column(name = "rg_dt", nullable = false)
    LocalDateTime rgDt;

    @NotNull
    @UpdateTimestamp
    @Column(name = "ud_dt", nullable = false)
    LocalDateTime udDt;

    @Builder
    public Refund(Purchase purchase, String reason, String status) {
        this.purchase = purchase;
        this.reason = reason;
        this.status = status;
    }

    public void updateStatus(String status){
        if (!"PENDING".equals(this.status)) throw new NotFoundException("보류 중인 환불만 변경 할수 있습니다.");

        this.status = status;
    }

}
