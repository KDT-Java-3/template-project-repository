package com.sparta.ecommerce.domain.refund.service;

import com.sparta.ecommerce.domain.order.entity.Order;
import com.sparta.ecommerce.domain.order.repository.OrderRepository;
import com.sparta.ecommerce.domain.product.entity.Product;
import com.sparta.ecommerce.domain.product.repository.ProductRepository;
import com.sparta.ecommerce.domain.refund.dto.RefundCreateRequest;
import com.sparta.ecommerce.domain.refund.dto.RefundResponse;
import com.sparta.ecommerce.domain.refund.dto.RefundUpdateRequest;
import com.sparta.ecommerce.domain.refund.entity.Refund;
import com.sparta.ecommerce.domain.refund.entity.RefundStatus;
import com.sparta.ecommerce.domain.refund.repository.RefundRepository;
import com.sparta.ecommerce.domain.user.entity.User;
import com.sparta.ecommerce.domain.user.repository.UserRepository;
import java.util.List;
import lombok.Generated;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RefundService {
    private final RefundRepository refundRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Transactional
    public RefundResponse createRefund(RefundCreateRequest dto) {
        Order order = (Order)this.orderRepository.getReferenceById(dto.getOrderId());
        User user = (User)this.userRepository.getReferenceById(dto.getUserId());
        Refund refund = Refund.builder().reason(dto.getReason()).order(order).user(user).build();
        this.refundRepository.save(refund);
        return RefundResponse.fromEntity(refund);
    }

    @Transactional
    public RefundResponse updateRefundProcess(RefundUpdateRequest dto) {
        Refund refund = (Refund)this.refundRepository.findById(dto.getRefundId()).orElseThrow(() -> new IllegalArgumentException("환불내역이 없습니다."));
        if (dto.getStatus() == RefundStatus.approved) {
            refund.approve();
            refund.getOrder().getOrderProductList().forEach((orderProduct) -> {
                Product product = orderProduct.getProduct();
                product.getId();
                int quantity = orderProduct.getQuantity();
                product.increase(quantity);
                this.productRepository.save(product);
            });
        } else if (dto.getStatus() == RefundStatus.rejected) {
            refund.reject();
        }

        return RefundResponse.fromEntity(refund);
    }

    @Transactional(
            readOnly = true
    )
    public List<RefundResponse> readRefundByUser(Long id) {
        List<Refund> refunds = this.refundRepository.findByUserId(id);
        List<RefundResponse> res = refunds.stream().map(RefundResponse::fromEntity).toList();
        return res;
    }

    @Generated
    public RefundService(final RefundRepository refundRepository, final OrderRepository orderRepository, final UserRepository userRepository, final ProductRepository productRepository) {
        this.refundRepository = refundRepository;
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }
}

