package com.example.demo.service.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserResponse {
    private final Long id;
    private final String username;
    private final String email;
}