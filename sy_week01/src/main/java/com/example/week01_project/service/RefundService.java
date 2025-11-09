package com.example.week01_project.service;

import com.example.week01_project.common.BadRequestException;
import com.example.week01_project.common.NotFoundException;
import com.example.week01_project.domain.orders.Orders;
import com.example.week01_project.domain.product.Product;
import com.example.week01_project.domain.refund.Refund;
import com.example.week01_project.dto.refund.RefundRequest;
import com.example.week01_project.repository.OrderRepository;
import com.example.week01_project.repository.RefundQueryRepository;
import com.example.week01_project.repository.RefundRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class RefundService {
    private final RefundRepository refundRepo;
    private final RefundQueryRepository refundQueryRepo;
    private final OrderRepository orderRepo;

    public RefundService(RefundRepository refundRepo, @Qualifier("refundQueryRepositoryImpl") RefundQueryRepository refundQueryRepo, OrderRepository orderRepo) {
        this.refundRepo = refundRepo; this.refundQueryRepo = refundQueryRepo; this.orderRepo = orderRepo;
    }

    @Transactional
    public Long request(RefundRequest req){
        Orders order = orderRepo.findById(req.orderId()).orElseThrow(()-> new NotFoundException("order not found"));
        if (order.getStatus() != Orders.Status.COMPLETED) throw new BadRequestException("only completed order refundable");
        Refund r = new Refund();
        r.setUserId(req.userId());
        r.setOrder(order);
        r.setReason(req.reason());
        refundRepo.save(r);
        return r.getId();
    }

    @Transactional
    public void approve(Long refundId){
        Refund r = refundRepo.findById(refundId).orElseThrow(()-> new NotFoundException("refund not found"));
        r.setStatus(Refund.Status.APPROVED);
        // 재고 복원 + 주문 상태 업데이트(요구사항)
        Orders o = r.getOrder();
        Product p = o.getProduct();
        p.setStock(p.getStock() + o.getQuantity());
        o.setStatus(Orders.Status.REFUNDED);
    }

    @Transactional
    public void reject(Long refundId){
        Refund r = refundRepo.findById(refundId).orElseThrow(()-> new NotFoundException("refund not found"));
        r.setStatus(Refund.Status.REJECTED);
    }

    public Page<Refund> search(Long userId, Refund.Status status, Pageable pageable){
        return refundQueryRepo.search(userId, status, pageable);
    }
}
