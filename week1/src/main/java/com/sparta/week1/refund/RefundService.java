package com.sparta.week1.refund;

import com.sparta.week1.dto.refund.CreateRefundDto;
import com.sparta.week1.entity.Order;
import com.sparta.week1.entity.Product;
import com.sparta.week1.entity.Refund;
import com.sparta.week1.entity.User;
import com.sparta.week1.order.OrderRepository;
import com.sparta.week1.product.ProductRepository;
import com.sparta.week1.user.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class RefundService {
    private final RefundRepository repository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public RefundService(RefundRepository repository, OrderRepository orderRepository, UserRepository userRepository, ProductRepository productRepository) {
        this.repository = repository;
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public Refund createRefund(CreateRefundDto dto) {
        Order order = orderRepository.findById(dto.getOrderId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 주문내역입니다."));

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        Refund refund = Refund.builder().order(order).user(user).build();

        return repository.save(refund);
    }

    @Transactional
    public Refund updateRefund(long id, CreateRefundDto dto) {
        Refund refund = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 환불정보입니다."));
        Order order = orderRepository.findById(dto.getOrderId()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 주문정보입니다."));
        Product product = productRepository.findById(order.getProduct().getId()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));

        if (dto.getStatus() != null) {
            refund.updateStatus(dto.getStatus());

            // 환불 시 재고 복구
            product.updateStock(product.getStock() + order.getQuantity());
        }

        return refund;
    }

    public Refund getRefundById(long id) {
        Refund refund = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 환불정보입니다."));
        return refund;
    }
}
