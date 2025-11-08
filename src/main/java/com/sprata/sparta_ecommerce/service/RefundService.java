package com.sprata.sparta_ecommerce.service;

import com.sprata.sparta_ecommerce.dto.RefundRequestDto;
import com.sprata.sparta_ecommerce.dto.RefundResponseDto;
import com.sprata.sparta_ecommerce.entity.RefundStatus;

import java.util.List;

public interface RefundService {
    RefundResponseDto requestRefund(RefundRequestDto refundRequestDto);

    RefundResponseDto processRefund(Long refundId, RefundStatus status);

    List<RefundResponseDto> getRefundsByUserId(Long userId);
}