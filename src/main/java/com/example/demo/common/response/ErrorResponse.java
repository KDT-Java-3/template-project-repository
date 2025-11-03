package com.example.demo.common.response;

import lombok.Builder;

import java.util.Map;

@Builder
public record ErrorResponse(String errorCode, String errorMsg, Map<String, Object> errorInfo) {
}
