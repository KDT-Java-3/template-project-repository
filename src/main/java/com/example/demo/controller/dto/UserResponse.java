package com.example.demo.controller.dto;

import com.example.demo.service.dto.UserDto;

import java.math.BigDecimal;
import java.util.List;

public record UserResponse(Long id, String username, String email, int purchaseCount, BigDecimal point) {

    public static UserResponse from(UserDto dto) {
        return new UserResponse(dto.getId(), dto.getUsername(), dto.getEmail(), dto.getPurchaseCount(), dto.getPoint());
    }

    public static List<UserResponse> from(List<UserDto> dtos) {
        return dtos.stream()
                .map(UserResponse::from)
                .toList();
    }
}
