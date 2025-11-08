package com.sprata.sparta_ecommerce.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductRequestDto {

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotNull
    @Size(min = 100)
    private Long price;

    @PositiveOrZero
    private int stock;

    @NotNull
    private Long category_id;
}
