package com.sparta.project1.domain.refund.service;

import com.sparta.project1.domain.PageResponse;
import com.sparta.project1.domain.order.api.dto.OrderResponse;
import com.sparta.project1.domain.order.service.OrderFindService;
import com.sparta.project1.domain.refund.api.dto.RefundResponse;
import com.sparta.project1.domain.refund.entity.Refund;
import com.sparta.project1.domain.refund.repository.RefundRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RefundFindService {
    private final RefundRepository refundRepository;
    private final OrderFindService orderFindService;

    public Refund getById(Long refundId) {
        return refundRepository.findById(refundId)
                .orElseThrow(() -> new NoSuchElementException("refund not found"));
    }


    public PageResponse<RefundResponse> getAllByUserId(Long userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "id");
        Page<Refund> refunds = refundRepository.findAllByUserId(userId, pageable);

        Page<RefundResponse> refundResponses = refunds.map(refund -> {
            OrderResponse orderResponse = orderFindService.getOrderResponse(refund.getOrders());
            return new RefundResponse(refund.getId(),
                    orderResponse,
                    refund.getStatus().name(),
                    refund.getReason(),
                    refund.getCreatedAt());
        });

        return PageResponse.of(refundResponses, refundResponses.getContent());
    }
}
