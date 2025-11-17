package com.example.demo.domain.order.dto.response;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 주문 항목 응답 DTO
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemResponse {

    private Long id;
    private Long productId;
    private String productName;  // Product 정보 조인 시 포함
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;  // quantity * unitPrice
}
