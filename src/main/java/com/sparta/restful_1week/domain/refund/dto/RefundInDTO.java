package com.sparta.restful_1week.domain.refund.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefundInDTO {
    private Long id;
    private String userId;
    private String orderId;
    private String reason;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
