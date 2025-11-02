package com.sparta.project1.domain.refund.service;

import com.sparta.project1.domain.order.domain.OrderStatus;
import com.sparta.project1.domain.order.domain.Orders;
import com.sparta.project1.domain.order.domain.ProductOrder;
import com.sparta.project1.domain.order.domain.ProductOrderEvent;
import com.sparta.project1.domain.order.service.OrderFindService;
import com.sparta.project1.domain.refund.api.dto.RefundRegisterRequest;
import com.sparta.project1.domain.refund.entity.Refund;
import com.sparta.project1.domain.refund.entity.RefundStatus;
import com.sparta.project1.domain.refund.repository.RefundRepository;
import com.sparta.project1.domain.user.domain.Users;
import com.sparta.project1.domain.user.service.UsersFindService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class RefundModifyService {
    private final RefundRepository refundRepository;
    private final RefundFindService refundFindService;
    private final UsersFindService usersFindService;
    private final OrderFindService orderFindService;
    private final ApplicationEventPublisher eventPublisher;

    public void register(Long orderId, Long userId, RefundRegisterRequest request) {
        Orders orders = orderFindService.getById(orderId);
        Users users = usersFindService.getById(userId);

        if (orders.getStatus() != OrderStatus.COMPLETED) {
            throw new IllegalStateException("cannot refund, order status is not completed");
        }

        Refund refund = Refund.register(orders, users, request.reason());
        refundRepository.save(refund);
    }

    public void changeStatus(Long refundId, String status) {
        Refund refund = refundFindService.getById(refundId);

        refund.changeStatus(status);

        refundRepository.save(refund);
        if (refund.getStatus() == RefundStatus.APPROVED) {
            List<ProductOrder> productOrders = refund.getOrders().getProductOrders();
            eventPublisher.publishEvent(new ProductOrderEvent(productOrders));
        }
    }
}
