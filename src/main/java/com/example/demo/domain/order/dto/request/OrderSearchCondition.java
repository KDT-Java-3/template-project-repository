package com.example.demo.domain.order.dto.request;

import com.example.demo.domain.order.entity.OrderStatus;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 주문 검색 조건 DTO
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderSearchCondition {

    private Long userId;
    private OrderStatus status;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
