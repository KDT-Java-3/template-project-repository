package com.example.demo.service.dto;

import java.math.BigDecimal;

public record UserCreateCommand(String username, String email, String password, String point) {
}

