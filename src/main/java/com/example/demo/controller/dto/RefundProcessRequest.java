package com.example.demo.controller.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RefundProcessRequest {
    @NotBlank(message = "처리 상태는 필수입니다 (APPROVED 또는 REJECTED)")
    private String status;
}

