package com.sparta.demo.refund.service;

import com.sparta.demo.order.domain.Order;
import com.sparta.demo.order.domain.OrderRepository;
import com.sparta.demo.refund.domain.Refund;
import com.sparta.demo.refund.domain.RefundRepository;
import com.sparta.demo.refund.service.command.RefundProcessCommand;
import com.sparta.demo.refund.service.command.RefundRequestCommand;
import com.sparta.demo.user.domain.User;
import com.sparta.demo.user.domain.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class RefundService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final RefundRepository refundRepository;

    public Long request(RefundRequestCommand command) {
        User user = userRepository.getById(command.userId());
        Order order = orderRepository.getById(command.orderId());

        Refund refund = Refund.create(user, order, command.reason());
        refundRepository.save(refund);
        return refund.getId();
    }

    @Transactional(readOnly = true)
    public List<Refund> findAllByUserId(Long userId) {
        return refundRepository.findByUserId(userId);
    }

    public void process(RefundProcessCommand command) {
        Refund refund = refundRepository.getById(command.refundId());

        if (command.approved()) {
            refund.approve();
            return;
        }
        refund.reject();
    }
}
