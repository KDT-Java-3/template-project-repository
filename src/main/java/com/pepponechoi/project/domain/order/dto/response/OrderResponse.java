package com.pepponechoi.project.domain.order.dto.response;

import com.pepponechoi.project.common.enums.OrderStatus;
import com.pepponechoi.project.domain.product.dto.response.ProductResponse;
import java.time.LocalDateTime;

public record OrderResponse(
    Long id,
    String status,
    LocalDateTime orderedAt,
    ProductResponse product
) {

}
