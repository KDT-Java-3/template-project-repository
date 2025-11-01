package com.sparta.restful_1week.domain.refund.controller;

import com.sparta.restful_1week.domain.refund.dto.RefundInDTO;
import com.sparta.restful_1week.domain.refund.dto.RefundOutDTO;
import com.sparta.restful_1week.domain.refund.service.RefundService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RefundController {
    @PostMapping("/orders")
    public RefundOutDTO createRefund(@RequestBody RefundInDTO refundInDTO) {
        RefundService orderService = new RefundService();
        return orderService.createRefund(refundInDTO);
    }

    @GetMapping("/orders")
    public List<RefundOutDTO> getRefunds() {
        RefundService refundService = new RefundService();
        return refundService.getRefunds();
    }

    @PutMapping("/orders/{id}")
    public RefundOutDTO updateRefund(@PathVariable Long id, @RequestBody RefundInDTO refundInDTO) {
        RefundService refundService = new RefundService();
        return refundService.updateRefund(id, refundInDTO);
    }
}
