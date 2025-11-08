package com.sprata.sparta_ecommerce.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@NoArgsConstructor
@Setter
@ToString
public class CategoryRequestDto {

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotNull
    private Long parent_id;
}
