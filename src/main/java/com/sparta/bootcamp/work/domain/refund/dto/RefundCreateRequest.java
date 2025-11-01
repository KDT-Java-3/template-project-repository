package com.sparta.bootcamp.work.domain.refund.dto;

import com.sparta.bootcamp.work.common.enums.RefundStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefundCreateRequest {

    Long orderId;
    Long userId;

}
