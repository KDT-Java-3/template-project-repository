package com.study.shop.domain.dto;

import com.study.shop.domain.entity.Product;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;


public class ProductDto {

    @Getter
    @Setter
    @NoArgsConstructor
    public static class CreateProduct {
        @NotBlank
        private String name;

        @NotNull
        @PositiveOrZero
        private BigDecimal price;

        @NotNull
        @PositiveOrZero
        private Integer stock;

        @NotNull
        private Long categoryId;

        @NotBlank
        private String description;
    }

    @Getter
    @Setter
    public static class UpdateProduct {
        @NotBlank
        private String name;

        @PositiveOrZero
        private BigDecimal price;

        @PositiveOrZero
        private Integer stock;

        @NotNull
        private Long categoryId;

        @NotBlank
        private String description;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @Builder
    public static class SearchProduct {
        private Long categoryId;
        private BigDecimal minPrice;
        private BigDecimal maxPrice;
        private String keyword;
    }

    @Builder
    @Getter
    public static class Resp {
        private Long id;
        private String name;
        private String description;
        private BigDecimal price;
        private Integer stock;
        private Long categoryId;
        private String categoryName;

        public static Resp of(Product p) {
            return Resp.builder()
                    .id(p.getId())
                    .name(p.getName())
                    .description(p.getDescription())
                    .price(p.getPrice())
                    .stock(p.getStock())
                    .categoryId(p.getCategory().getId())
                    .categoryName(p.getCategory().getName())
                    .build();
        }

        public static List<Resp> fromList(List<Product> products) {
            return products.stream()
                    .map(Resp::of)
                    .collect(Collectors.toList());
        }
    }
}
