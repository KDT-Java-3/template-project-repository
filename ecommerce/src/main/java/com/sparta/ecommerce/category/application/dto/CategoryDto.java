package com.sparta.ecommerce.category.application.dto;

import com.sparta.ecommerce.category.domain.Category;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;


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
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CategoryResponse{
        private Long id;
        private String name;
        private String description;
        private LocalDateTime createdAt;

        public static CategoryResponse fromEntity(Category category) {
            return CategoryResponse.builder()
                    .id(category.getId())
                    .name(category.getName())
                    .description(category.getDescription())
                    .createdAt(category.getCreatedAt())
                    .build();
        }
    }
}
