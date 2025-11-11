package com.example.demo.lecture.refactorsection1;

import com.example.demo.common.ServiceException;
import com.example.demo.common.ServiceExceptionCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * Spring Service 레이어 리팩토링 실습 (Before).
 * - 검증, 주문 생성, 가격 계산, 응답 변환이 한 메서드에 몰려있음.
 * - 안전하지 않은 파라미터 검증/총액 검사 로직이 뒤섞여 있어 SRP 위반 사례로 사용한다.
 */
@Service
public class RefactorSection1OrderServiceBefore {

    private final RefactorSection1OrderRepository orderRepository;

    public RefactorSection1OrderServiceBefore(RefactorSection1OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Transactional
    public RefactorSection1OrderResponse placeOrder(RefactorSection1OrderRequest request) {
        if (request.userId() == null || request.lines().isEmpty()) {
            throw new ServiceException(ServiceExceptionCode.INVALID_ORDER_QUANTITY);
        }

        RefactorSection1Order order = new RefactorSection1Order(null, request.userId());
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (RefactorSection1OrderRequest.OrderLineRequest lineRequest : request.lines()) {
            if (lineRequest.quantity() <= 0) {
                throw new ServiceException(ServiceExceptionCode.INVALID_ORDER_QUANTITY);
            }
            RefactorSection1OrderLine line = new RefactorSection1OrderLine(
                    lineRequest.productId(), lineRequest.unitPrice(), lineRequest.quantity()
            );
            order.addLine(line);
            totalPrice = totalPrice.add(
                    lineRequest.unitPrice().multiply(BigDecimal.valueOf(lineRequest.quantity()))
            );
        }

        if (totalPrice.compareTo(BigDecimal.valueOf(1_000_000)) > 0) {
            throw new ServiceException(ServiceExceptionCode.INVALID_ORDER_QUANTITY);
        }

        RefactorSection1Order saved = orderRepository.save(order);
        return RefactorSection1OrderResponse.from(saved);
    }
}
