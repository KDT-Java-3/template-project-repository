package com.sparta.sangmin.controller.dto;

public record RefundProcessRequest(
        String action  // "approve" or "reject"
) {
}
