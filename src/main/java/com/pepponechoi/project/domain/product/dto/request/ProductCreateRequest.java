package com.pepponechoi.project.domain.product.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductCreateRequest {
    @NotBlank(message = "이름은 필수입니다.")
    private String name;
    private String description;
    @PositiveOrZero(message = "가격은 0이상이어야 합니다.")
    private Long price;
    @PositiveOrZero(message = "재고는 0이상이어야 합니다.")
    private Long stock;
    @NotBlank(message = "카테고리 ID는 필수입니다.")
    private Long categoryId;
    @NotBlank(message = "유저 ID는 필수입니다.")
    private Long userId; // 이건 나중에 JWT와 스프링 시큐리티를 통해 유저 데이터를 받는 것이 맞음.
}
