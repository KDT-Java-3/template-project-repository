package com.example.demo.domain.product.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductUpdateRequest {

    @Size(max = 150, message = "상품명은 최대 150자까지 입력 가능합니다.")
    private String name;

    private String description;

    @DecimalMin(value = "0.0", inclusive = false, message = "가격은 0보다 커야 합니다.")
    private BigDecimal price;

    @Min(value = 0, message = "재고는 0 이상이어야 합니다.")
    private Integer stock;

    private Long categoryId;
}
