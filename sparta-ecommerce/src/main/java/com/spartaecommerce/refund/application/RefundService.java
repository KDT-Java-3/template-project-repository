package com.spartaecommerce.refund.application;

import com.spartaecommerce.common.exception.BusinessException;
import com.spartaecommerce.common.exception.ErrorCode;
import com.spartaecommerce.order.domain.entity.Order;
import com.spartaecommerce.order.domain.repository.OrderRepository;
import com.spartaecommerce.product.domain.service.ProductStockService;
import com.spartaecommerce.refund.application.dto.RefundInfo;
import com.spartaecommerce.refund.domain.command.RefundCreateCommand;
import com.spartaecommerce.refund.domain.command.RefundProcessCommand;
import com.spartaecommerce.refund.domain.entity.Refund;
import com.spartaecommerce.refund.domain.entity.RefundStatus;
import com.spartaecommerce.refund.domain.query.RefundSearchQuery;
import com.spartaecommerce.refund.domain.repository.RefundRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RefundService {

    private final RefundRepository refundRepository;
    private final OrderRepository orderRepository;
    private final ProductStockService productStockService;

    @Transactional
    public Long create(RefundCreateCommand createCommand) {
        validateRefundCreation(createCommand.orderId());

        Refund refund = Refund.createNew(createCommand);
        return refundRepository.save(refund);
    }

    @Transactional
    public void process(RefundProcessCommand processCommand) {
        Refund refund = refundRepository.getById(processCommand.refundId());

        if (processCommand.status() == RefundStatus.APPROVED) {
            processApproval(refund);
        } else if (processCommand.status() == RefundStatus.REJECTED) {
            processRejection(refund);
        } else {
            throw new BusinessException(
                ErrorCode.INVALID_REQUEST,
                "Invalid refund status: " + processCommand.status()
            );
        }

        refundRepository.save(refund);
    }

    public List<RefundInfo> search(RefundSearchQuery searchQuery) {
        List<Refund> refunds = refundRepository.search(searchQuery);
        return refunds.stream()
            .map(RefundInfo::from)
            .toList();
    }

    private void validateRefundCreation(Long orderId) {
        Order order = orderRepository.getById(orderId);

        if (!order.isComplete()) {
            throw new BusinessException(
                ErrorCode.INVALID_REQUEST,
                "Only completed orders can be refunded. orderStatus: " + order.getStatus()
            );
        }

        refundRepository.findByOrderId(orderId)
            .ifPresent(existingRefund -> {
                throw new BusinessException(
                    ErrorCode.ENTITY_ALREADY_EXISTS,
                    "refundId: " + existingRefund.getRefundId()
                );
            });
    }

    private void processApproval(Refund refund) {
        refund.approve();

        Order order = orderRepository.getById(refund.getOrderId());
        productStockService.restoreForOrderItems(order.getOrderItems());
    }

    private void processRejection(Refund refund) {
        refund.reject();
    }
}