package com.sparta.commerce.management.dto.request.product;

import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductRequest {
    @NotNull(message = "상품명은 필수입니다")
    String name;

    String description;

    @NotNull(message = "가격은 0보다 커야 합니다.")
    @DecimalMin(value = "0.0", inclusive = false, message = "가격은 0보다 커야 합니다.")
    BigDecimal price;

    @NotNull(message = "재고는 0 이상이어야 합니다.")
    @Min(value = 0, message = "재고는 0 이상이어야 합니다.")
    Integer stock;

    @NotNull(message = "카테고리는 필수입니다")
    UUID categoryId;
}

