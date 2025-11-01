package com.sparta.restful_1week.domain.refund.entity;

import com.sparta.restful_1week.domain.refund.dto.RefundInDTO;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Table
@Entity
@Getter
@Setter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Refund {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private String orderId;

    @Column(nullable = false)
    private String reason;

    @Column(nullable = false)
    @CreationTimestamp
    LocalDateTime createdAt;

    @Column(nullable = false)
    @UpdateTimestamp
    LocalDateTime updatedAt;

    @Builder
    public Refund(RefundInDTO refundInDTO) {
        this.userId = refundInDTO.getUserId();
        this.orderId = refundInDTO.getOrderId();
        this.reason = refundInDTO.getReason();
        this.createdAt = refundInDTO.getCreatedAt();
        this.updatedAt = refundInDTO.getUpdatedAt();
    }

    @Builder
    public void updateRefund(RefundInDTO refundInDTO) {
        this.userId = refundInDTO.getUserId();
        this.orderId = refundInDTO.getOrderId();
        this.reason = refundInDTO.getReason();
        this.updatedAt = refundInDTO.getUpdatedAt();
    }
}
