package com.example.stproject.domain.product.dto;

import com.example.stproject.domain.category.entity.Category;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductCreateRequest {
    @NotBlank(message = "상품명은 필수입니다.")
    @Size(max = 50, message = "상품명은 50자 이하로 입력해주세요.")
    private String name;

    private String description;

    @NotNull(message = "가격은 필수입니다.")
    @Min(value = 0, message = "가격은 0 이상이어야 합니다.")
    private Long price;

    @NotNull
    @Min(0)
    private Integer stock;

     @NotNull
     private Long categoryId;
}
