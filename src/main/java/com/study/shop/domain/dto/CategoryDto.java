package com.study.shop.domain.dto;

import com.study.shop.domain.entity.Category;
import com.study.shop.domain.entity.Product;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class CategoryDto {

    @Getter
    @Setter
    @NoArgsConstructor
    public static class CreateCategory {

        @NotBlank
        private String name;
        private String description;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class UpdateCategory {

        @NotNull
        private BigInteger id;

        private String name;
        private String description;
    }

    @Getter
    @Builder
    public static class ProductBrief {
        private Long id;
        private String name;
        private Integer stock;

        public static ProductBrief of(Product p) {
            return ProductBrief.builder()
                    .id(p.getId())
                    .name(p.getName())
                    .stock(p.getStock())
                    .build();
        }
    }

    @Getter
    @Builder
    public static class Resp {
        private Long id;
        private String name;
        private String description;
        private List<ProductBrief> products;

        public static Resp of(Category c, boolean withProducts) {
            return Resp.builder()
                    .id(c.getId())
                    .name(c.getName())
                    .description(c.getDescription())
                    .products(withProducts && c.getProducts() != null ? c.getProducts().stream().map(ProductBrief::of).toList() : null)
                    .build();
        }
    }
}
