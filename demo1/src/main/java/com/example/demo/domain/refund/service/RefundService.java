package com.example.demo.domain.refund.service;

import com.example.demo.domain.Status;
import com.example.demo.domain.order.entity.Order;
import com.example.demo.domain.order.entity.OrderProduct;
import com.example.demo.domain.order.repository.OrderProductRepository;
import com.example.demo.domain.order.repository.OrderRepository;
import com.example.demo.domain.product.entity.Product;
import com.example.demo.domain.refund.dto.RefundRequestDto;
import com.example.demo.domain.refund.dto.RefundResponseDto;
import com.example.demo.domain.refund.entity.Refund;
import com.example.demo.domain.refund.repository.RefundRepository;
import com.example.demo.domain.user.entity.User;
import com.example.demo.domain.user.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RefundService {
    private final RefundRepository refundRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final OrderProductRepository orderProductRepository;

    public RefundService(RefundRepository refundRepository, UserRepository userRepository, OrderRepository orderRepository, OrderProductRepository orderProductRepository) {
        this.refundRepository = refundRepository;
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.orderProductRepository = orderProductRepository;
    }

    @Transactional
    public RefundResponseDto createRefund(RefundRequestDto request){
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Order order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        if(order.getStatus() != Status.PENDING){
            throw new IllegalArgumentException("Order status must be PENDING");
        }

        Refund refund = Refund.builder()
                .user(user)
                .order(order)
                .status(Status.PENDING)
                .reason(request.getReason())
                .build();
        Refund saved = refundRepository.save(refund);
        return RefundResponseDto.builder()
                .refundId(saved.getId())
                .userId(saved.getUser().getId())
                .orderId(saved.getOrder().getId())
                .reason(saved.getReason())
                .status(saved.getStatus())
                .build();
    }

    @Transactional
    public void completeRefund(Long userId, Long refundId){
        // TODO : 관리자 검증 로직 추가(테이블도 수정해야함)
        User user  = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Refund refund = refundRepository.findById(refundId)
                .orElseThrow(() -> new IllegalArgumentException("Refund not found"));

        if(refund.getStatus() != Status.PENDING){
            throw new IllegalArgumentException("Refund status must be PENDING");
        }

        List<OrderProduct> orderProduct = orderProductRepository.findByOrderIdWithProduct(refund.getOrder().getId());

        for(OrderProduct product : orderProduct){
            product.getProduct().increaseStock(product.getQuantity());
        }
        refund.getOrder().updateOrderStatus(Status.CANCELED);
        refund.updateStatus(Status.COMPLETED);
    }

    @Transactional
    public void cancelRefund(Long userId, Long refundId){
        // TODO : 관리자 검증 로직 추가(테이블도 수정해야함)
        User user  = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Refund refund = refundRepository.findById(refundId)
                .orElseThrow(() -> new IllegalArgumentException("Refund not found"));

        if(refund.getStatus() != Status.PENDING){
            throw new IllegalArgumentException("Refund status must be PENDING");
        }
        refund.updateStatus(Status.CANCELED);
    }

    public List<RefundResponseDto> getAllRefunds(){
        List<Refund> refunds = refundRepository.findAll();
        return refunds.stream()
                .map(refund -> new RefundResponseDto(refund))
                .collect(Collectors.toList());
    }

    public List<RefundResponseDto> getRefundsById(Long id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        List<Refund> refunds = refundRepository.findRefundsByUserId(id);

        return refunds.stream()
                .map(refund -> new RefundResponseDto(refund))
                .collect(Collectors.toList());
    }
}
