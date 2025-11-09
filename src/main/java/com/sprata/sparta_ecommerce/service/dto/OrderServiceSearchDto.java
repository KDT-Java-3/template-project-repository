package com.sprata.sparta_ecommerce.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderServiceSearchDto {
    private Long userId;
    private String orderStatus;
    private LocalDate startDate;
    private LocalDate endDate;
}
