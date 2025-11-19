package io.depark.commerceservice.controller.dto;

import io.depark.commerceservice.entity.enums.PurchaseStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class PurchaseSearchCondition {

    private Long userId;

    private PurchaseStatus status;

    private LocalDate startDate;

    private LocalDate endDate;
}
