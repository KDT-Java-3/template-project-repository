package com.sparta.demo.service;

import com.sparta.demo.domain.order.Order;
import com.sparta.demo.domain.refund.Refund;
import com.sparta.demo.domain.refund.RefundStatus;
import com.sparta.demo.domain.user.User;
import com.sparta.demo.dto.refund.RefundCreateDto;
import com.sparta.demo.dto.refund.RefundDto;
import com.sparta.demo.repository.OrderRepository;
import com.sparta.demo.repository.RefundRepository;
import com.sparta.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RefundService {

    private final RefundRepository refundRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    @Transactional
    public RefundDto createRefund(RefundCreateDto dto) {
        Order order = orderRepository.findById(dto.getOrderId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "주문을 찾을 수 없습니다. ID: " + dto.getOrderId()));

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다. ID: " + dto.getUserId()));

        Refund refund = Refund.create(order, user, dto.getReason());
        Refund savedRefund = refundRepository.save(refund);
        return RefundDto.from(savedRefund);
    }

    public RefundDto getRefund(Long id) {
        Refund refund = refundRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "환불을 찾을 수 없습니다. ID: " + id));
        return RefundDto.from(refund);
    }

    public List<RefundDto> getRefundsByUserId(Long userId) {
        return refundRepository.findByUser_Id(userId).stream()
                .map(RefundDto::from)
                .collect(Collectors.toList());
    }

    public List<RefundDto> getRefundsByUserIdAndStatus(Long userId, RefundStatus status) {
        return refundRepository.findByUser_IdAndStatus(userId, status).stream()
                .map(RefundDto::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public RefundDto approveRefund(Long id) {
        Refund refund = refundRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "환불을 찾을 수 없습니다. ID: " + id));

        try {
            refund.approve();
            refund.getOrder().getOrderItems().forEach(orderItem -> {
                orderItem.getProduct().increaseStock(orderItem.getQuantity());
            });
        } catch (IllegalStateException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

        return RefundDto.from(refund);
    }

    @Transactional
    public RefundDto rejectRefund(Long id) {
        Refund refund = refundRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "환불을 찾을 수 없습니다. ID: " + id));

        try {
            refund.reject();
        } catch (IllegalStateException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

        return RefundDto.from(refund);
    }
}
