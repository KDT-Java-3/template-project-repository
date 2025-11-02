package com.sparta.work.category.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestCreateCategoryDto {
    @NotBlank
    private String name;

    @NotBlank
    private String description;
}
