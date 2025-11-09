package com.example.demo.service.dto;

import com.example.demo.PurchaseStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PurchaseSearchCondition {
    Long userId;
    Long productId;
    PurchaseStatus status;
    LocalDateTime purchasedAfter;
    LocalDateTime purchasedBefore;
    Integer minQuantity;
    Integer maxQuantity;
}
