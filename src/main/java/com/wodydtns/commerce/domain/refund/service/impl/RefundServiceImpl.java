package com.wodydtns.commerce.domain.refund.service.impl;

import com.wodydtns.commerce.domain.member.entity.Member;
import com.wodydtns.commerce.domain.member.repository.MemberRepository;
import com.wodydtns.commerce.domain.order.entity.Order;
import com.wodydtns.commerce.domain.order.repository.OrderRepository;
import com.wodydtns.commerce.domain.product.entity.Product;
import com.wodydtns.commerce.domain.product.repository.ProductRepository;
import com.wodydtns.commerce.domain.refund.dto.CreateRefundRequest;
import com.wodydtns.commerce.domain.refund.dto.SearchRefundResponse;
import com.wodydtns.commerce.domain.refund.dto.UpdateRefundRequest;
import com.wodydtns.commerce.domain.refund.entity.Refund;
import com.wodydtns.commerce.domain.refund.repository.RefundRepository;
import com.wodydtns.commerce.domain.refund.service.RefundService;
import com.wodydtns.commerce.global.enums.RefundStatus;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RefundServiceImpl implements RefundService {

        private final RefundRepository refundRepository;
        private final MemberRepository memberRepository;
        private final OrderRepository orderRepository;
        private final ProductRepository productRepository;

        @Override
        @Transactional
        public void createRefund(CreateRefundRequest createRefundRequest) {
                Member member = memberRepository.findByUserId(createRefundRequest.getUserId())
                                .orElseThrow(() -> new RuntimeException("회원을 찾을 수 없습니다."));
                Order order = orderRepository.findById(createRefundRequest.getOrderId())
                                .orElseThrow(() -> new RuntimeException("주문을 찾을 수 없습니다."));

                Refund refund = Refund.builder()
                                .member(member)
                                .order(order)
                                .refundStatus(RefundStatus.PENDING)
                                .reason(createRefundRequest.getReason())
                                .build();
                refundRepository.save(refund);
        }

        @Override
        public SearchRefundResponse searchRefund(long id) {
                Refund refund = refundRepository.findByMember_Id(id)
                                .orElseThrow(() -> new RuntimeException("환불 정보를 조회할 수 없습니다."));
                return SearchRefundResponse.builder()
                                .reason(refund.getReason())
                                .refundStatus(refund.getRefundStatus())
                                .createdAt(refund.getCreatedAt().toLocalDate())
                                .build();
        }

        @Override
        @Transactional
        public void updateRefund(UpdateRefundRequest updateRefundRequest) {
                Refund refund = refundRepository.findById(updateRefundRequest.getId())
                                .orElseThrow(() -> new RuntimeException("환불 정보를 찾을 수 없습니다."));
                refund.updateRefundStatus(updateRefundRequest.getRefundStatus());
                refundRepository.save(refund);
                if (updateRefundRequest.getRefundStatus() == RefundStatus.APPROVED) {
                        Product product = productRepository.findById(updateRefundRequest.getProductId())
                                        .orElseThrow(() -> new RuntimeException("상품을 찾을 수 없습니다."));
                        product.increaseStock();
                        productRepository.save(product);
                }
        }

}
