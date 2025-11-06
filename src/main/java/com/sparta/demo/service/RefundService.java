package com.sparta.demo.service;

import com.sparta.demo.domain.order.Order;
import com.sparta.demo.domain.order.OrderStatus;
import com.sparta.demo.domain.refund.Refund;
import com.sparta.demo.domain.refund.RefundStatus;
import com.sparta.demo.domain.user.User;
import com.sparta.demo.exception.ServiceException;
import com.sparta.demo.exception.ServiceExceptionCode;
import com.sparta.demo.repository.OrderRepository;
import com.sparta.demo.repository.RefundRepository;
import com.sparta.demo.repository.UserRepository;
import com.sparta.demo.service.dto.refund.RefundCreateDto;
import com.sparta.demo.service.dto.refund.RefundDto;
import com.sparta.demo.service.mapper.RefundServiceMapper;
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
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final RefundServiceMapper mapper;

    @Transactional
    public RefundDto createRefund(RefundCreateDto dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new ServiceException(
                        ServiceExceptionCode.USER_NOT_FOUND, "ID: " + dto.getUserId()));

        Order order = orderRepository.findById(dto.getOrderId())
                .orElseThrow(() -> new ServiceException(
                        ServiceExceptionCode.ORDER_NOT_FOUND, "ID: " + dto.getOrderId()));

        // COMPLETED 상태의 주문만 환불 요청 가능
        if (order.getStatus() != OrderStatus.COMPLETED) {
            throw new ServiceException(
                    ServiceExceptionCode.INVALID_ORDER_STATUS,
                    "COMPLETED 상태의 주문만 환불 요청이 가능합니다. 현재 상태: " + order.getStatus());
        }

        Refund refund = Refund.create(order, user, dto.getReason());
        Refund savedRefund = refundRepository.save(refund);
        return mapper.toDto(savedRefund);
    }

    public RefundDto getRefund(Long id) {
        Refund refund = refundRepository.findById(id)
                .orElseThrow(() -> new ServiceException(
                        ServiceExceptionCode.REFUND_NOT_FOUND, "ID: " + id));
        return mapper.toDto(refund);
    }

    public List<RefundDto> getRefundsByUserId(Long userId) {
        return refundRepository.findByUser_Id(userId).stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    public List<RefundDto> getRefundsByUserIdAndStatus(Long userId, RefundStatus status) {
        return refundRepository.findByUser_IdAndStatus(userId, status).stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public RefundDto approveRefund(Long id) {
        Refund refund = refundRepository.findById(id)
                .orElseThrow(() -> new ServiceException(
                        ServiceExceptionCode.REFUND_NOT_FOUND, "ID: " + id));

        refund.approve();
        refund.getOrder().getOrderItems().forEach(orderItem -> {
            orderItem.getProduct().increaseStock(orderItem.getQuantity());
        });

        return mapper.toDto(refund);
    }

    @Transactional
    public RefundDto rejectRefund(Long id) {
        Refund refund = refundRepository.findById(id)
                .orElseThrow(() -> new ServiceException(
                        ServiceExceptionCode.REFUND_NOT_FOUND, "ID: " + id));

        refund.reject();

        return mapper.toDto(refund);
    }
}
