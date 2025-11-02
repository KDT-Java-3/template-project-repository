package com.sparta.templateprojectrepository.dto.request;

import com.sparta.templateprojectrepository.entity.Category;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
public class ProductCreateRequestDto {

    private Long categoryId;

    @NotBlank(message = "상품이름을 입력하세요")
    @Size(min = 1, max = 100)
    private String productName;

    @NotBlank(message = "상품가격을 입력하세요")
    @Digits(integer = 8, fraction = 0, message = "숫자형식으로 입력하세요.")
    private BigDecimal price;

    @NotBlank(message = "상품수량을 입력하세요")
    @Digits(integer = 4, fraction = 0, message = "숫자형식으로 입력하세요.")
    private int stock;

    private String description;

}
