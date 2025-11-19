package io.depark.commerceservice.controller.dto;

import io.depark.commerceservice.entity.enums.RefundStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class RefundSearchCondition {

    private RefundStatus status;

    private LocalDate startDate;

    private LocalDate endDate;
}
