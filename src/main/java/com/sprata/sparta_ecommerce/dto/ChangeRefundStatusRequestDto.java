package com.sprata.sparta_ecommerce.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ChangeRefundStatusRequestDto {
    @NotBlank
    private String refund_status;
}
