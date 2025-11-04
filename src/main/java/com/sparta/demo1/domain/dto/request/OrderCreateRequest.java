package com.sparta.demo1.domain.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderCreateRequest {

  @NotNull(message = "사용자 ID는 필수입니다.")
  private Long userId;

  @NotNull(message = "상품 ID는 필수입니다.")
  private Long productId;

  @NotNull(message = "수량은 필수입니다.")
  @Min(value = 1, message = "수량은 1개 이상이어야 합니다.")
  private Long quantity;

  @NotBlank(message = "배송 주소는 필수입니다.")
  private String shippingAddress;
}
