package com.sprata.sparta_ecommerce.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ChangeOrderStatusRequestDto {
    @NotBlank
    private String order_status;
}
