package com.sparta.templateprojectrepository.dto.request;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
public class ProductFindRequestDto {
    @NotBlank(message = "상품ID를 입력하세요")
    private Long productId;
}
