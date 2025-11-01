package com.sprata.sparta_ecommerce.dto;

import jakarta.validation.constraints.NotBlank;
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
    private String description;
}
