package com.sparta.demo1.domain.category.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class CategoryReqDto {

    @Getter
    @Setter
    @NoArgsConstructor
    public static class CategoryRegisterDto {
        @NotBlank(message = "카테고리 이름은 필수입니다.")
        @Size(min = 2, max = 30, message = "카테고리 이름은 2이상 30이하여야 합니다.")
        private String name;

        @NotBlank(message = "카테고리 설명은 필수입니다.")
        private String description;

        @NotBlank(message = "카테고리 부모 값은 필수입니다.")
        @PositiveOrZero(message = "카테리고 부모 값은 0이상입니다.")
        private Long parentId;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class CategoryUpdateDto {

        @NotBlank(message = "카테고리 ID 값은 필수입니다.")
        private Long id;

        @Size(min = 2, max = 30, message = "카테고리 이름은 2이상 30이하여야 합니다.")
        private String name;
        private String description;
        private Long parentId;
    }
}
