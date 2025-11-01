package com.sparta.ecommerce.category.application.dto;

import com.sparta.ecommerce.category.domain.Category;
import com.sparta.ecommerce.product.application.dto.ProductDto;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


public class CategoryDto {


    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CategoryCreateRequest{
        @NotNull
        @Length(min = 2, max = 100, message = "카테고리명은 2자이상 100자 이하로 입력해주세요.")
        private String name;

        private String description;

        public Category toEntity() {
            return Category.builder()
                    .name(this.name)
                    .description(this.description)
                    .build();
        }
    }

    @Getter
    public static class CategoryPatchRequest extends CategoryCreateRequest{
    }

    @Getter
    @SuperBuilder
    public static class CategoryResponse extends CommonCategoryResponse{

        public static CategoryResponse fromEntity(Category category) {
            return CategoryResponse.builder()
                    .id(category.getId())
                    .name(category.getName())
                    .description(category.getDescription())
                    .createdAt(category.getCreatedAt())
                    .build();
        }
    }

    @Getter
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CategoryWithProductResponse extends CommonCategoryResponse{

        List<ProductDto.ProductResponse> products;

        public static CategoryWithProductResponse fromEntity(Category category) {
            return CategoryWithProductResponse.builder()
                    .id(category.getId())
                    .name(category.getName())
                    .description(category.getDescription())
                    .createdAt(category.getCreatedAt())
                    .products(category.getProducts()
                            .stream().map(ProductDto.ProductResponse::fromEntity)
                            .collect(Collectors.toList())
                    )
                    .build();
        }
    }


}
