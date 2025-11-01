package com.sparta.bootcamp.java_2_example.service;

import com.sparta.bootcamp.java_2_example.common.enums.ErrorCode;
import com.sparta.bootcamp.java_2_example.common.enums.RefundStatus;
import com.sparta.bootcamp.java_2_example.domain.order.entity.Order;
import com.sparta.bootcamp.java_2_example.domain.order.entity.OrderItem;
import com.sparta.bootcamp.java_2_example.domain.order.repository.OrderRepository;
import com.sparta.bootcamp.java_2_example.domain.product.entity.Product;
import com.sparta.bootcamp.java_2_example.domain.refund.entity.Refund;
import com.sparta.bootcamp.java_2_example.domain.refund.repository.RefundRepository;
import com.sparta.bootcamp.java_2_example.domain.user.entity.User;
import com.sparta.bootcamp.java_2_example.domain.user.repository.UserRepository;
import com.sparta.bootcamp.java_2_example.dto.request.RefundRequest;
import com.sparta.bootcamp.java_2_example.dto.response.RefundResponse;
import com.sparta.bootcamp.java_2_example.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class RefundService {

    private final RefundRepository refundRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    @Transactional
    public RefundResponse createRefund(RefundRequest request) {
        log.info("환불 요청 생성: 사용자 ID {}, 주문 ID {}", request.getUserId(), request.getOrderId());

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND, "사용자를 찾을 수 없습니다. ID: " + request.getUserId()));

        Order order = orderRepository.findByIdWithItems(request.getOrderId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND, "주문을 찾을 수 없습니다. ID: " + request.getOrderId()));

        // 이미 환불 요청이 있는지 확인
        if (refundRepository.existsByOrderId(order.getId())) {
            throw new CustomException(ErrorCode.REFUND_ALREADY_REQUESTED);
        }

        // 주문 상태 확인 (주문취소 상태는 환불 불가능)
        if (order.canBeCanceled()) {
            throw new CustomException(ErrorCode.INVALID_REFUND_STATUS);
        }

        Refund refund = Refund.builder()
                .order(order)
                .user(user)
                .reason(request.getReason())
                .build();

        Refund savedRefund = refundRepository.save(refund);
        log.info("환불 요청 생성 완료: ID {}", savedRefund.getId());

        return RefundResponse.from(savedRefund);
    }

    public RefundResponse getRefund(Long id) {
        Refund refund = refundRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND, "환불 요청을 찾을 수 없습니다. ID: " + id));
        return RefundResponse.from(refund);
    }

    public List<RefundResponse> getRefundsByUser(Long userId) {
        return refundRepository.findByUserId(userId).stream()
                .map(RefundResponse::from)
                .collect(Collectors.toList());
    }

    public List<RefundResponse> getRefundsByUserAndStatus(Long userId, RefundStatus status) {
        return refundRepository.findByUserIdAndStatus(userId, status).stream()
                .map(RefundResponse::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public RefundResponse approveRefund(Long id) {
        log.info("환불 승인 요청: ID {}", id);

        Refund refund = refundRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND, "환불 요청을 찾을 수 없습니다. ID: " + id));

        refund.approve();

        // 주문 상태가 COMPLETED 인것만 취소
        Order order = refund.getOrder();
        order.refund();

        // 재고 복원
        for (OrderItem orderItem : order.getOrderItems()) {
            Product product = orderItem.getProduct();
            product.increaseStock(orderItem.getQuantity());
        }

        log.info("환불 승인 완료: ID {}", id);
        return RefundResponse.from(refund);
    }

    @Transactional
    public RefundResponse rejectRefund(Long id) {
        log.info("환불 거절 요청: ID {}", id);

        Refund refund = refundRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND, "환불 요청을 찾을 수 없습니다. ID: " + id));

        refund.reject();

        log.info("환불 거절 완료: ID {}", id);
        return RefundResponse.from(refund);
    }
}
