package com.example.week01_project.service;

import com.example.week01_project.domain.orders.OrderItem;
import com.example.week01_project.domain.refund.*;
import com.example.week01_project.dto.refund.RefundDtos.*;
import com.example.week01_project.repository.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RefundService {
    private final RefundRepository refundRepo;
    private final RefundItemRepository refundItemRepo;
    private final OrderItemRepository orderItemRepo;
    private final ProductRepository productRepo;

    @Transactional
    public Resp request(RequestReq req) {
        Refund refund = Refund.builder()
                .orderId(req.orderId())
                .userId(req.userId())
                .status(RefundStatus.pending)
                .reason(req.reason())
                .build();
        refundRepo.save(refund);
        return new Resp(refund.getId(), refund.getStatus().name());
    }

    @Transactional
    public Resp process(Long refundId, ProcessReq req) {
        Refund refund = refundRepo.findById(refundId).orElseThrow(() -> new EntityNotFoundException("refund not found"));
        if (refund.getStatus() != RefundStatus.pending) {
            throw new IllegalStateException("already processed");
        }

        if ("approve".equalsIgnoreCase(req.action())) {
            refund.setStatus(RefundStatus.approved);
            refund.setApprovedBy(req.adminUserId());
            refund.setApprovedAt(Instant.now());

            // 환불 항목 기반 재고 복원
            List<RefundItem> items = refundItemRepo.findByRefundId(refundId);
            for (RefundItem ri : items) {
                OrderItem oi = orderItemRepo.findById(ri.getOrderItemId())
                        .orElseThrow(() -> new EntityNotFoundException("order item not found"));
                var p = productRepo.findByIdForUpdate(oi.getProductId())
                        .orElseThrow(() -> new EntityNotFoundException("product not found"));
                p.setStock(p.getStock() + ri.getQuantity());
            }
        } else if ("reject".equalsIgnoreCase(req.action())) {
            refund.setStatus(RefundStatus.rejected);
        } else {
            throw new IllegalStateException("invalid action");
        }
        return new Resp(refund.getId(), refund.getStatus().name());
    }

    @Transactional
    public void addRefundItem(Long refundId, Long orderItemId, Integer qty, BigDecimal amount) {
        Refund refund = refundRepo.findById(refundId).orElseThrow(() -> new EntityNotFoundException("refund not found"));
        if (refund.getStatus() != RefundStatus.pending) throw new IllegalStateException("refund not pending");

        RefundItem ri = RefundItem.builder()
                .refundId(refund.getId())
                .orderItemId(orderItemId)
                .quantity(qty)
                .amount(amount)
                .build();
        refundItemRepo.save(ri);
    }

    @Transactional(readOnly = true)
    public List<Refund> listByUser(Long userId) {
        return refundRepo.findByUserIdOrderByCreatedAtDesc(userId);
    }
}
