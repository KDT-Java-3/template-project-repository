package com.sparta.ecommerce.product.application.dto;

import com.sparta.ecommerce.category.application.dto.CategoryDto;
import com.sparta.ecommerce.category.domain.Category;
import com.sparta.ecommerce.product.domain.Product;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ProductDto {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProductSearchRequest{
        private Long categoryId;
        private String name;
        private BigDecimal minPrice;
        private BigDecimal maxPrice;
    }


    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProductCreateRequest{

        @NotNull(message = "상품명을 입력해주세요.")
        private String name;
        
        private String description;
        
        @NotNull(message = "가격을 입력해주세요.")
        private BigDecimal price;

        @NotNull(message = "재고를 입력해주세요.")
        private Integer stock;
        
        @NotNull(message = "카테고리를 선택해주세요.")
        private Long categoryId;

        public Product toEntity(Category category) {
            return Product.builder()
                    .name(this.name)
                    .description(this.description)
                    .price(this.price)
                    .stock(this.stock)
                    .category(category)
                    .build();

        }
    }

    public static class ProductUpdateRequest extends ProductCreateRequest{

    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProductResponse{
        private Long id;
        private String name;
        private String description;
        private BigDecimal price;
        private Integer stock;
        private CategoryDto.CategoryResponse category;
        private LocalDateTime createdAt;

        public static ProductResponse fromEntity(Product product){
            return ProductResponse.builder()
                    .id(product.getId())
                    .name(product.getName())
                    .description(product.getDescription())
                    .price(product.getPrice())
                    .stock(product.getStock())
                    .createdAt(product.getCreatedAt())
                    .category(CategoryDto.CategoryResponse.fromEntity(product.getCategory()))
                    .build();
        }
    }

}
