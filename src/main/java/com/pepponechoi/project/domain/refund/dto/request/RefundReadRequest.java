package com.pepponechoi.project.domain.refund.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RefundReadRequest {
    @NotBlank(message = "유저의 ID는 필수입니다.")
    Long userId;
}
