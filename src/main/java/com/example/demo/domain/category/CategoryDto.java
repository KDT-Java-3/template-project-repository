package com.example.demo.domain.category;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;

public class CategoryDto {
    // ============================================
    // Request DTO
    // - name, description, parent(상위 카테고리)
    // ============================================
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Request {
        @NotBlank(message = "Name is required")
        private String name;
        private String description;
        private Long parentId;
    }


    // ============================================
    // Response DTO
    // - name, description, parent(상위 카테고리)
    // - id, createdAt, updatedAt
    // ============================================
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {
        private Long id;
        private String name;
        private String description;
        private Long parentId;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        /**
         * Category 엔티티를 Response DTO로 변환하는 정적 팩토리 메서드
         *
         * 사용 예시:
         * User user = userRepository.findById(1L).orElseThrow();
         * UserDto.Response response = UserDto.Response.from(user);
         * return ResponseEntity.ok(response);
         */
        public static Response from(Category category) {
            return Response.builder()
                    .id(category.getId())
                    .name(category.getName())
                    .description(category.getDescription())
                    .parentId(category.getParentId().getId())
                    .createdAt(category.getCreatedAt())
                    .updatedAt(category.getUpdatedAt())
                    .build();
        }

    }
}
