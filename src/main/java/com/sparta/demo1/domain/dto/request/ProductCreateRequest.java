package com.sparta.demo1.domain.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductCreateRequest {

  @NotBlank(message = "상품 이름은 필수 입력입니다.")
  private String name;

  private String description;

  @NotNull(message = "상품 가격은 필수 입력입니다.")
  @Min(value = 0, message = "가격은 0 이상이어야 합니다.")
  private Long price;

  @NotNull(message = "상품 재고는 필수 입력입니다.")
  @Min(value = 0, message = "재고는 0 이상이어야 합니다.")
  private Long stock;

  @NotNull(message = "카테고리는 필수 입력입니다.")
  private Long categoryId;
}
