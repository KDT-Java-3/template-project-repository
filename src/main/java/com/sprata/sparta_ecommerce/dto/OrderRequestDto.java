package com.sprata.sparta_ecommerce.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestDto {

    @NotNull
    private Long user_id;

    @NotNull
    private Long product_id;

    @NotNull
    @Size(min = 1, max = 1000)
    private int quantity;

    @NotBlank
    private String shipping_address;
}
