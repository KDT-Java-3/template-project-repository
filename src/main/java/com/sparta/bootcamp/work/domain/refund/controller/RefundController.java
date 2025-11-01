package com.sparta.bootcamp.work.domain.refund.controller;

import com.sparta.bootcamp.work.domain.refund.dto.RefundCreateRequest;
import com.sparta.bootcamp.work.domain.refund.dto.RefundDto;
import com.sparta.bootcamp.work.domain.refund.dto.RefundRequest;
import com.sparta.bootcamp.work.domain.refund.service.RefundService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RefundController {

    private final RefundService refundService;


    @PostMapping("/refund")
    ResponseEntity<String> createRefund(@RequestBody RefundCreateRequest refundRequest) {
        Long id =  refundService.createRefund(refundRequest);
        return ResponseEntity.ok().body("Refund created with id: " + id);
    }

    @GetMapping("/refund")
    ResponseEntity<List<RefundDto>> getRefundList(@RequestBody RefundRequest refundRequest) {
        return ResponseEntity.ok().body(refundService.getRefund(refundRequest));
    }

    @PutMapping("/refund")
    ResponseEntity<RefundDto> editRefund(@RequestBody RefundRequest refundRequest) {
        return ResponseEntity.ok().body(refundService.editRefund(refundRequest));
    }


}
