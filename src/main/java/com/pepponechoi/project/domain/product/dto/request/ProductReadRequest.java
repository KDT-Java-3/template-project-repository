package com.pepponechoi.project.domain.product.dto.request;

import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductReadRequest {
    Long categoryId;
    @PositiveOrZero
    Long priseFrom;
    @PositiveOrZero
    Long priseTo;
    String q;
}
