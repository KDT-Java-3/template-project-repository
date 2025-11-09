package com.sparta.demo1.domain.product.dto.request;

import com.sparta.demo1.domain.product.enums.ProductOrderBy;
import com.sparta.demo1.domain.product.enums.ProductStockFilter;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

public class ProductReqDto {

    @Getter
    @Setter
    @NoArgsConstructor
    public static class ProductFilterDto {
        private List<Long> filterCategoryIdList;
        private BigDecimal minPrice;
        private BigDecimal maxPrice;
        private String nameKeyWord;
        private ProductStockFilter stockFilter;
        private List<ProductOrderBy> productOrderByList;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class ProductRegisterDto {
        @NotBlank(message = "이름을 입력해주세요.")
        @Size(min = 2, max = 10, message = "이름은 2자 이상 10자리 이하여야 합니다.")
        private String name;

        @NotBlank(message = "제품 설명을 입력해주세요.")
        private String description;

        @PositiveOrZero(message = "가격은 0이거나 0보다 커야합니다.")
        @NotBlank(message = "가격을 입력해주세요.")
        private BigDecimal price;

        @PositiveOrZero(message = "제품 수는 0이거나 0보다 커야합니다.")
        @NotBlank(message = "제품 수를 입력해주세요.")
        private Integer stock;

        @NotBlank(message = "관련 카테고리를 입력해주세요.")
        private Long categoryId;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class ProductUpdateDto {
        @NotBlank(message = "제품 ID 필수값을 입력해주세요.")
        private Long id;

        @NotBlank(message = "이름을 입력해주세요.")
        @Size(min = 2, max = 10, message = "이름은 2자 이상 10자리 이하여야 합니다.")
        private String name;

        @NotBlank(message = "제품 설명을 입력해주세요.")
        private String description;

        @PositiveOrZero(message = "가격은 0이거나 0보다 커야합니다.")
        @NotBlank(message = "가격을 입력해주세요.")
        private BigDecimal price;

        @PositiveOrZero(message = "제품 수는 0이거나 0보다 커야합니다.")
        @NotBlank(message = "제품 수를 입력해주세요.")
        private Integer stock;

        @NotBlank(message = "관련 카테고리를 입력해주세요.")
        private Long categoryId;
    }
}
