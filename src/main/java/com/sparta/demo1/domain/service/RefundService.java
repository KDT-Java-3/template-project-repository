package com.sparta.demo1.domain.service;

import com.sparta.demo1.domain.dto.request.RefundCreateRequest;
import com.sparta.demo1.domain.dto.response.RefundResponse;
import com.sparta.demo1.domain.entity.Order;
import com.sparta.demo1.domain.entity.OrderStatus;
import com.sparta.demo1.domain.entity.Product;
import com.sparta.demo1.domain.entity.Refund;
import com.sparta.demo1.domain.entity.RefundStatus;
import com.sparta.demo1.domain.entity.User;
import com.sparta.demo1.domain.repository.OrderRepository;
import com.sparta.demo1.domain.repository.RefundRepository;
import com.sparta.demo1.domain.repository.UserRepository;
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

  @Transactional
  public RefundResponse createRefund(RefundCreateRequest request) {
    // 사용자 조회
    User user = userRepository.findById(request.getUserId())
        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

    // 주문 조회
    Order order = orderRepository.findById(request.getOrderId())
        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 주문입니다."));

    // 주문이 완료된 상태인지 확인
    if (order.getOrderStatus() != OrderStatus.COMPLETED) {
      throw new IllegalStateException("완료된 주문만 환불 요청할 수 있습니다.");
    }

    // 중복 환불 요청 확인
    refundRepository.findByOrder(order).ifPresent(refund -> {
      throw new IllegalStateException("이미 환불 요청된 주문입니다.");
    });

    // 환불 생성
    Refund refund = Refund.builder()
        .user(user)
        .order(order)
        .reason(request.getReason())
        .build();

    Refund savedRefund = refundRepository.save(refund);
    return RefundResponse.from(savedRefund);
  }

  public RefundResponse getRefund(Long refundId) {
    Refund refund = refundRepository.findById(refundId)
        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 환불 요청입니다."));
    return RefundResponse.from(refund);
  }

  public List<RefundResponse> getRefundsByUser(Long userId) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

    List<Refund> refunds = refundRepository.findByUserOrderByCreatedAtDesc(user);
    return refunds.stream()
        .map(RefundResponse::from)
        .collect(Collectors.toList());
  }

  public List<RefundResponse> getRefundsByStatus(RefundStatus status) {
    List<Refund> refunds = refundRepository.findByStatus(status);
    return refunds.stream()
        .map(RefundResponse::from)
        .collect(Collectors.toList());
  }

  @Transactional
  public RefundResponse approveRefund(Long refundId) {
    Refund refund = refundRepository.findById(refundId)
        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 환불 요청입니다."));

    // 환불 승인
    refund.approve();

    // 재고 복구
    Order order = refund.getOrder();
    Product product = order.getProduct();
    product.increaseStock(order.getQuantity());

    // 주문 상태를 취소로 변경
    order.cancel();

    return RefundResponse.from(refund);
  }

  @Transactional
  public RefundResponse rejectRefund(Long refundId) {
    Refund refund = refundRepository.findById(refundId)
        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 환불 요청입니다."));

    // 환불 거부
    refund.reject();

    return RefundResponse.from(refund);
  }
}