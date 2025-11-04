package com.sprata.sparta_ecommerce.service;

import com.sprata.sparta_ecommerce.controller.exception.DataNotFoundException;
import com.sprata.sparta_ecommerce.dto.RefundRequestDto;
import com.sprata.sparta_ecommerce.dto.RefundResponseDto;
import com.sprata.sparta_ecommerce.entity.*;
import com.sprata.sparta_ecommerce.repository.OrderRepository;
import com.sprata.sparta_ecommerce.repository.RefundRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RefundServiceImpl implements RefundService {

    private final RefundRepository refundRepository;
    private final OrderRepository orderRepository;

    @Override
    @Transactional
    public RefundResponseDto requestRefund(RefundRequestDto refundRequestDto) {
        Order order = orderRepository.findById(refundRequestDto.getOrder_id())
                .orElseThrow(() -> new DataNotFoundException("해당 주문을 찾을 수 없습니다."));

        Refund refund = Refund.builder()
                        .userId(refundRequestDto.getUser_id())
                        .order(order)
                        .reason(refundRequestDto.getReason())
                        .build();

        Refund savedRefund = refundRepository.save(refund);
        return new RefundResponseDto(savedRefund);
    }

    @Override
    @Transactional
    public RefundResponseDto processRefund(Long refundId, RefundStatus status) {
        Refund refund = refundRepository.findById(refundId)
                .orElseThrow(() -> new IllegalArgumentException("해당 환불 요청을 찾을 수 없습니다."));

        if (refund.getStatus() != RefundStatus.PENDING) {
            throw new IllegalArgumentException("이미 처리된 환불 요청입니다.");
        }

        Order order = Optional.ofNullable(refund.getOrder())
                .filter(o -> o.getStatus() == OrderStatus.CANCELED)
                .orElseThrow(() -> new DataNotFoundException("취소된 주문이 아니거나, 주문 정보를 찾을 수 없습니다."));

        Product product = order.getProduct();
        product.updateStock(product.getStock() + order.getQuantity());
        refund.updateStatus(status);

        return new RefundResponseDto(refund);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RefundResponseDto> getRefundsByUserId(Long userId) {
        return refundRepository.findByUserId(userId).stream()
                .map(RefundResponseDto::new)
                .collect(Collectors.toList());
    }
}
