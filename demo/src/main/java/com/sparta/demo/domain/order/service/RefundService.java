package com.sparta.demo.domain.order.service;

import com.sparta.demo.domain.order.dto.request.CreateRefundRequest;
import com.sparta.demo.domain.order.dto.request.ProcessRefundRequest;
import com.sparta.demo.domain.order.dto.response.RefundResponse;
import com.sparta.demo.domain.order.entity.Order;
import com.sparta.demo.domain.order.entity.Refund;
import com.sparta.demo.domain.order.entity.User;
import com.sparta.demo.domain.order.repository.OrderRepository;
import com.sparta.demo.domain.order.repository.ProductRepository;
import com.sparta.demo.domain.order.repository.RefundRepository;
import com.sparta.demo.domain.order.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RefundService {
    private final RefundRepository refundRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    // 환불 요청
    public RefundResponse createRefund(CreateRefundRequest request) throws Exception {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new Exception("유저 정보를 찾을 수 없습니다."));
        
        Order order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() -> new Exception("주문 정보를 찾을 수 없습니다."));

        // 이미 환불 요청이 존재하는지 확인
        boolean exists = refundRepository.findAll().stream()
                .anyMatch(refund -> refund.getOrder().getId().equals(request.getOrderId()) 
                        && refund.getStatus() == Refund.RefundStatus.PENDING);
        if (exists) {
            throw new Exception("이미 처리 대기 중인 환불 요청이 존재합니다.");
        }

        Refund saved = refundRepository.save(Refund.builder()
                .user(user)
                .order(order)
                .reason(request.getReason())
                .status(Refund.RefundStatus.PENDING)
                .build());
        
        return RefundResponse.buildFromEntity(saved);
    }

    // 환불 처리 (승인/거절)
    public RefundResponse processRefund(ProcessRefundRequest request) throws Exception {
        Refund refund = refundRepository.findById(request.getRefundId())
                .orElseThrow(() -> new Exception("환불 요청을 찾을 수 없습니다."));

        try {
            if (request.getAction() == Refund.RefundStatus.APPROVED) {
                refund.approve();
                productRepository.save(refund.getOrder().getProduct());
            } else if (request.getAction() == Refund.RefundStatus.REJECTED) {
                refund.reject();
            } else {
                throw new Exception("잘못된 처리 요청입니다.");
            }
            
            refundRepository.save(refund);
            return RefundResponse.buildFromEntity(refund);
        } catch (IllegalStateException e) {
            throw new Exception(e.getMessage());
        }
    }

    // 환불 조회 (사용자별)
    public List<RefundResponse> getRefundsByUserId(Long userId) {
        List<Refund> refunds = refundRepository.findAllByUserId(userId);
        return refunds.stream()
                .map(RefundResponse::buildFromEntity)
                .toList();
    }
}

