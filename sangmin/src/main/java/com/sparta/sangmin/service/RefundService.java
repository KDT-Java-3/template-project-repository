package com.sparta.sangmin.service;

import com.sparta.sangmin.controller.dto.RefundRequest;
import com.sparta.sangmin.controller.dto.RefundResponse;
import com.sparta.sangmin.domain.Order;
import com.sparta.sangmin.domain.Product;
import com.sparta.sangmin.domain.Refund;
import com.sparta.sangmin.domain.User;
import com.sparta.sangmin.repository.OrderRepository;
import com.sparta.sangmin.repository.RefundRepository;
import com.sparta.sangmin.repository.UserRepository;
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
    public RefundResponse requestRefund(RefundRequest request) {
        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다. ID: " + request.userId()));

        Order order = orderRepository.findById(request.orderId())
                .orElseThrow(() -> new IllegalArgumentException("주문을 찾을 수 없습니다. ID: " + request.orderId()));

        // 주문의 사용자와 요청 사용자가 동일한지 확인
        if (!order.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("해당 주문에 대한 환불 권한이 없습니다.");
        }

        Refund refund = new Refund(user, order, request.reason());
        Refund savedRefund = refundRepository.save(refund);

        return RefundResponse.from(savedRefund);
    }

    public List<RefundResponse> getRefundsByUserId(Long userId) {
        return refundRepository.findByUserId(userId).stream()
                .map(RefundResponse::from)
                .collect(Collectors.toList());
    }

    public RefundResponse getRefundById(Long id) {
        Refund refund = refundRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("환불 요청을 찾을 수 없습니다. ID: " + id));
        return RefundResponse.from(refund);
    }

    @Transactional
    public RefundResponse approveRefund(Long id) {
        Refund refund = refundRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("환불 요청을 찾을 수 없습니다. ID: " + id));

        refund.approve();

        // 환불 승인 시 재고 복원
        Order order = refund.getOrder();
        Product product = order.getProduct();
        product.increaseStock(order.getQuantity());

        return RefundResponse.from(refund);
    }

    @Transactional
    public RefundResponse rejectRefund(Long id) {
        Refund refund = refundRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("환불 요청을 찾을 수 없습니다. ID: " + id));

        refund.reject();
        return RefundResponse.from(refund);
    }
}
