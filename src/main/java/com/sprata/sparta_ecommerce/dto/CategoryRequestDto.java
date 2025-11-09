package com.sprata.sparta_ecommerce.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@NoArgsConstructor
@Setter
@ToString
@AllArgsConstructor
@Builder
public class CategoryRequestDto {

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotNull
    private Long parent_id;
}
