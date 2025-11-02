package com.sparta.demo.dto.refund;

import com.sparta.demo.domain.refund.RefundStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * Controller에서 클라이언트에게 반환하는 환불 응답 DTO
 */
@Getter
@AllArgsConstructor
public class RefundResponse {
    private Long id;
    private Long orderId;
    private Long userId;
    private String reason;
    private RefundStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static RefundResponse from(RefundDto dto) {
        return new RefundResponse(
                dto.getId(),
                dto.getOrderId(),
                dto.getUserId(),
                dto.getReason(),
                dto.getStatus(),
                dto.getCreatedAt(),
                dto.getUpdatedAt()
        );
    }
}
