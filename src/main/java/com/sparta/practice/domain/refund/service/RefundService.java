package com.sparta.practice.domain.refund.service;

import com.sparta.practice.common.exception.ResourceNotFoundException;
import com.sparta.practice.domain.order.entity.Order;
import com.sparta.practice.domain.order.repository.OrderRepository;
import com.sparta.practice.domain.refund.dto.RefundCreateRequest;
import com.sparta.practice.domain.refund.dto.RefundResponse;
import com.sparta.practice.domain.refund.entity.Refund;
import com.sparta.practice.domain.refund.entity.RefundStatus;
import com.sparta.practice.domain.refund.repository.RefundRepository;
import com.sparta.practice.domain.user.entity.User;
import com.sparta.practice.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RefundService {

    private final RefundRepository refundRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    @Transactional
    public RefundResponse createRefund(RefundCreateRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("사용자를 찾을 수 없습니다."));

        Order order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() -> new ResourceNotFoundException("주문을 찾을 수 없습니다."));

        Refund refund = Refund.builder()
                .user(user)
                .order(order)
                .reason(request.getReason())
                .build();

        Refund savedRefund = refundRepository.save(refund);
        return RefundResponse.from(savedRefund);
    }

    public RefundResponse getRefund(Long id) {
        Refund refund = refundRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("환불 요청을 찾을 수 없습니다."));
        return RefundResponse.from(refund);
    }

    public List<RefundResponse> getRefundsByUserId(Long userId) {
        return refundRepository.findByUserId(userId).stream()
                .map(RefundResponse::from)
                .collect(Collectors.toList());
    }

    public List<RefundResponse> getRefundsByUserIdAndStatus(Long userId, RefundStatus status) {
        return refundRepository.findByUserIdAndStatus(userId, status).stream()
                .map(RefundResponse::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public RefundResponse approveRefund(Long id) {
        Refund refund = refundRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("환불 요청을 찾을 수 없습니다."));
        refund.approve();
        return RefundResponse.from(refund);
    }

    @Transactional
    public RefundResponse rejectRefund(Long id) {
        Refund refund = refundRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("환불 요청을 찾을 수 없습니다."));
        refund.reject();
        return RefundResponse.from(refund);
    }
}
