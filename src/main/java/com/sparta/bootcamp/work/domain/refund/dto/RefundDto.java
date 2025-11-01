package com.sparta.bootcamp.work.domain.refund.dto;

import com.sparta.bootcamp.work.common.enums.RefundStatus;
import com.sparta.bootcamp.work.domain.refund.entity.Refund;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefundDto {

    RefundStatus status;
    LocalDateTime refundDate;
    String reason;

    public static RefundDto fromRefund(Refund refund) {
        return RefundDto.builder()
                .status(refund.getStatus())
                .reason(refund.getReason())
                .refundDate(refund.getCreatedAt())
                .build();

    }






}
