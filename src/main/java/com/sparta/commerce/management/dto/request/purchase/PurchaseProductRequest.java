package com.sparta.commerce.management.dto.request.purchase;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PurchaseProductRequest {

    @NotNull(message = "상품 ID는 필수입니다.")
    UUID id;

    @NotNull(message = "수량 필수입니다.")
    Integer quantity;
}
