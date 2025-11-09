package com.example.week01_project.dto.refund;

import com.example.week01_project.domain.refund.Refund;

public record RefundSearchRequest(Long userId, Refund.Status status) {}