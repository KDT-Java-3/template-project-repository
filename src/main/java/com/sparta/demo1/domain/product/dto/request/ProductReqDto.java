package com.sparta.demo1.domain.product.dto.request;

import jakarta.validation.constraints.NotBlank;
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
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class ProductRegisterDto {
        @NotBlank
        @Size(min = 2, max = 30)
        private String name;
        private String description;
        @NotBlank
        private BigDecimal price;
        @NotBlank
        private Integer stock;
        @NotBlank
        private Long categoryId;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class ProductUpdateDto {
        @NotBlank
        private Long id;
        @NotBlank
        @Size(min = 2, max = 30)
        private String name;
        private String description;
        @NotBlank
        private BigDecimal price;
        @NotBlank
        private Integer stock;
        @NotBlank
        private Long categoryId;
    }
}
