package com.sparta.demo.dto.refund;

import com.sparta.demo.domain.refund.Refund;
import com.sparta.demo.domain.refund.RefundStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * Service Layer에서 사용하는 환불 조회 DTO
 */
@Getter
@AllArgsConstructor
public class RefundDto {
    private Long id;
    private Long orderId;
    private Long userId;
    private String reason;
    private RefundStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static RefundDto from(Refund refund) {
        return new RefundDto(
                refund.getId(),
                refund.getOrder().getId(),
                refund.getUser().getId(),
                refund.getReason(),
                refund.getStatus(),
                refund.getCreatedAt(),
                refund.getUpdatedAt()
        );
    }
}
