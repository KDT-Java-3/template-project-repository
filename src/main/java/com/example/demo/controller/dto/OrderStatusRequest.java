package com.example.demo.controller.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderStatusRequest {
    @NotBlank(message = "주문 상태는 필수입니다")
    private String status;
}

