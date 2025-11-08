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
public class RefundRequestDto {
    @NotNull
    private Long user_id;
    @NotNull
    private Long order_id;
    @NotBlank
    private String reason;
}
