package com.sparta.bootcamp.work.domain.refund.service;

import com.sparta.bootcamp.work.common.enums.RefundStatus;
import com.sparta.bootcamp.work.domain.order.entity.Order;
import com.sparta.bootcamp.work.domain.order.entity.OrderProduct;
import com.sparta.bootcamp.work.domain.order.repository.OrderProductRepository;
import com.sparta.bootcamp.work.domain.order.repository.OrderRepository;
import com.sparta.bootcamp.work.domain.product.entity.Product;
import com.sparta.bootcamp.work.domain.product.repository.ProductRepository;
import com.sparta.bootcamp.work.domain.refund.dto.RefundCreateRequest;
import com.sparta.bootcamp.work.domain.refund.dto.RefundDto;
import com.sparta.bootcamp.work.domain.refund.dto.RefundRequest;
import com.sparta.bootcamp.work.domain.refund.entity.Refund;
import com.sparta.bootcamp.work.domain.refund.repository.RefundRepository;
import com.sparta.bootcamp.work.domain.user.entity.User;
import com.sparta.bootcamp.work.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.beans.Transient;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RefundService {

    ProductRepository productRepository;
    private final RefundRepository refundRepository;
    private final OrderRepository orderRepository;
    private final OrderProductRepository orderProductRepository;
    private final UserRepository userRepository;


    public Long createRefund(RefundCreateRequest refundRequest) {
        Order order = orderRepository.findById(refundRequest.getOrderId()).orElseThrow(()-> new RuntimeException("Order not found"));
        User user = userRepository.findById(refundRequest.getUserId()).orElseThrow(()-> new RuntimeException("User not found"));

        Refund refund = new Refund();
        refund.setOrder(order);
        refund.setUser(user);
        refund.setStatus(RefundStatus.PENDING);
        return refundRepository.save(refund).getId();
    }


    @Transactional
    public RefundDto editRefund(RefundRequest refundRequest) {
        Refund refund = refundRepository.findById(refundRequest.getRefundId()).orElseThrow(()-> new RuntimeException("Refund not found") );

        if(refund.getStatus().equals(RefundStatus.APPROVED)) {
            OrderProduct orderProduct = orderProductRepository.findByOrder(refund.getOrder()).orElseThrow(()-> new RuntimeException("Order product not found"));

            Product product = orderProduct.getProduct();
            product.setStock(product.getStock() + orderProduct.getQuantity());
            productRepository.save(product);
        }
        refund.setStatus(refundRequest.getStatus());

        return RefundDto.fromRefund(refundRepository.save(refund)) ;
    }


    public List<RefundDto> getRefund(RefundRequest refundRequest) {
        List<RefundDto>  refundDtoList = new ArrayList<>();

        User user = userRepository.findById(refundRequest.getUserId()).orElseThrow(()-> new RuntimeException("User not found"));
        List<Refund> refunds = refundRepository.findByUser(user);

        for (Refund refund : refunds) {
            refundDtoList.add(RefundDto.fromRefund(refund));
        }

        return refundDtoList;
    }

}
