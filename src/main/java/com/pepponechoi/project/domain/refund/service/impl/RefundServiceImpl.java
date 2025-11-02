package com.pepponechoi.project.domain.refund.service.impl;

import com.pepponechoi.project.common.enums.RefundStatus;
import com.pepponechoi.project.domain.order.entity.Order;
import com.pepponechoi.project.domain.order.repository.OrderRepository;
import com.pepponechoi.project.domain.refund.dto.request.RefundCreateRequest;
import com.pepponechoi.project.domain.refund.dto.request.RefundReadRequest;
import com.pepponechoi.project.domain.refund.dto.request.RefundUpdateRequest;
import com.pepponechoi.project.domain.refund.dto.response.RefundResponse;
import com.pepponechoi.project.domain.refund.entity.Refund;
import com.pepponechoi.project.domain.refund.repository.RefundRepository;
import com.pepponechoi.project.domain.refund.service.RefundService;
import com.pepponechoi.project.domain.user.entity.User;
import com.pepponechoi.project.domain.user.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RefundServiceImpl implements RefundService {
    private final RefundRepository refundRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;


    @Override
    @Transactional
    public RefundResponse createRefund(RefundCreateRequest request) {
        Order order = orderRepository.findById(request.getOrderId()).orElse(null);
        if (order == null) {
            return null;
        }
        User user = userRepository.findById(request.getUserId()).orElse(null);
        if (!order.getUser().equals(user)) {
            return null;
        }
        Refund refund = new Refund(user, order, request.getReason());
        refundRepository.save(refund);
        return new RefundResponse(refund.getId(), refund.getOrder().getId(), refund.getStatus(),
            refund.getCreatedAt().toLocalDate(), refund.getReason());
    }

    @Override
    public List<RefundResponse> getRefundsByUser(RefundReadRequest user) {
        List<Refund> refunds = refundRepository.findAllByUser_Id(user.getUserId());
        return refunds.stream().map(
            refund -> new RefundResponse(
                refund.getId(),
                refund.getOrder().getId(),
                refund.getStatus(),
                refund.getCreatedAt().toLocalDate(),
                refund.getReason()
            )
        ).toList();
    }

    @Override
    @Transactional
    public Boolean update(Long id, RefundUpdateRequest request) {
        Refund refund = refundRepository.findById(id).orElse(null);
        if (refund == null) {
            return false;
        }

        if (refund.getStatus().equals(request.getStatus()) || !refund.getStatus().equals(
            RefundStatus.PENDING)) {
            return false;
        }

        if (request.getStatus().equals(RefundStatus.APPROVED)) {
            refund.approve();
        }

        if (request.getStatus().equals(RefundStatus.REJECTED)) {
            refund.reject();
        }

        return true;
    }
}
