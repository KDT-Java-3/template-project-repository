package com.sparta.demo1.domain.category.dto.request;

import jakarta.validation.constraints.NotBlank;
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
        @NotBlank
        @Size(min = 2, max = 30)
        private String name;
        private String description;
        private Long parentId;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class CategoryUpdateDto {

        @NotBlank
        private Long id;

        @NotBlank
        @Size(min = 2, max = 30)
        private String name;
        private String description;
    }
}
