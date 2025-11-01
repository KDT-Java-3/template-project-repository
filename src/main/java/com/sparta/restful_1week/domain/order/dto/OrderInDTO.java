package com.sparta.restful_1week.domain.order.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderInDTO {
    private Long id;
    private String userId;
    private String productId;
    private Integer quantity;
    private String shippingAddress;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
