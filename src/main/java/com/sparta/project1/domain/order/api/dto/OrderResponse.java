package com.sparta.project1.domain.order.api.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderResponse(
        Long orderId,
        List<OrderedProductResponse> productInfos,
        BigDecimal totalPrice,
        String status,
        String shippingAddress,
        LocalDateTime createdAt
) {
}
