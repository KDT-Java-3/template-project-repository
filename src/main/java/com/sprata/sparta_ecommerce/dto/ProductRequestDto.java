package com.sprata.sparta_ecommerce.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    private String description;

    @NotNull
    @Size(min = 100)
    private Long price;

    @NotNull
    @Size(min = 0)
    private int stock;

    @NotNull
    private Long category_id;
}
