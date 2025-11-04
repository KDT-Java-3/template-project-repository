package com.sparta.project.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public class CategoryDto {
    // 카테고리 생성 요청 DTO
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class CategoryCreateRequest {

        @NotBlank(message = "카테고리 이름은 필수입니다.")
        @Size(max = 100, message = "카테고리 이름은 100자를 초과할 수 없습니다.")
        private String name;

        @Size(max = 500, message = "설명은 500자를 초과할 수 없습니다.")
        private String description;
    }

    // 카테고리 수정 요청 DTO
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class CategoryUpdateRequest {

        @NotBlank(message = "카테고리 이름은 필수입니다.")
        @Size(max = 100, message = "카테고리 이름은 100자를 초과할 수 없습니다.")
        private String name;

        @Size(max = 500, message = "설명은 500자를 초과할 수 없습니다.")
        private String description;
    }

    // 카테고리 응답 DTO (간단한 버전)
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class CategoryResponse {

        private Long id;
        private String name;
        private String description;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }

    // 카테고리 응답 DTO (상품 정보 포함)
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class CategoryWithProductsResponse {

        private Long id;
        private String name;
        private String description;
        private List<ProductSummary> products;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        // 상품 요약 정보
        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        @Builder
        public static class ProductSummary {
            private Long id;
            private String name;
            private Double price;
        }
    }
}