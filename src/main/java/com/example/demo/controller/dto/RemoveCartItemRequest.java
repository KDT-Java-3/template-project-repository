package com.example.demo.controller.dto;

import jakarta.validation.constraints.NotBlank;

public record RemoveCartItemRequest(
        @NotBlank(message = "아이템 이름은 필수입니다.")
        String itemName
) {
}
