package com.sparta.demo.service.dto.refund;

import com.sparta.demo.controller.dto.refund.RefundRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Service Layer에서 사용하는 환불 생성 DTO
 */
@Getter
@AllArgsConstructor
public class RefundCreateDto {
    private Long orderId;
    private Long userId;
    private String reason;

    public static RefundCreateDto from(RefundRequest request) {
        return new RefundCreateDto(
                request.getOrderId(),
                request.getUserId(),
                request.getReason()
        );
    }
}
