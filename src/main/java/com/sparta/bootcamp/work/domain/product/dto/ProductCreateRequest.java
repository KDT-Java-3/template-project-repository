package com.sparta.bootcamp.work.domain.product.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductCreateRequest {

    @NotBlank(message = "이름은 필수입니다.")
    @Size(min = 2, max = 50)
    private String name;

    @NotBlank(message = "가격은 필수입니다.")
    private Double price;

    @NotBlank(message = "수량은 필수입니다.")
    private Integer stock;

    @NotBlank(message = "카테고리 ID 필수입니다.")
    private Long categoryId;

    private String description;
}
