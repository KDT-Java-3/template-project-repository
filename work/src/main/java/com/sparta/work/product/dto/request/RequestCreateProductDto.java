package com.sparta.work.product.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestCreateProductDto {
    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotNull
    @Min(value = 0, message = "가격은 0 이상이어야 합니다.")
    private Integer price;

    @NotNull
    @Min(value = 0, message = "재고는 0 이상이어야 합니다.")
    private Integer stock;

    @NotNull
    private Long categoryId;
}
