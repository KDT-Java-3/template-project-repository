package com.sparta.demo.refund.controller;

import com.sparta.demo.refund.controller.request.RefundProcessRequest;
import com.sparta.demo.refund.controller.request.RefundRequestRequest;
import com.sparta.demo.refund.controller.response.RefundFindAllResponse;
import com.sparta.demo.refund.domain.Refund;
import com.sparta.demo.refund.service.RefundService;
import com.sparta.demo.refund.service.command.RefundProcessCommand;
import com.sparta.demo.refund.service.command.RefundRequestCommand;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RefundController {

    private final RefundService refundService;

    @PostMapping("/refunds")
    public ResponseEntity<Void> request(@RequestBody RefundRequestRequest request) {
        RefundRequestCommand command = request.toCommand();
        Long id = refundService.request(command);
        return ResponseEntity.created(URI.create("/refunds/" + id)).build();
    }

    @GetMapping("/users/{userId}/refunds")
    public ResponseEntity<List<RefundFindAllResponse>> findByUserId(@PathVariable Long userId) {
        List<Refund> refunds = refundService.findAllByUserId(userId);
        List<RefundFindAllResponse> responses = refunds.stream()
                .map(RefundFindAllResponse::of)
                .toList();
        return ResponseEntity.ok(responses);
    }

    @PatchMapping("/admin/refunds/{refundId}/process")
    public ResponseEntity<Void> process(
            @PathVariable Long refundId,
            @RequestBody RefundProcessRequest request
    ) {
        RefundProcessCommand command = request.toCommand(refundId);
        refundService.process(command);
        return ResponseEntity.noContent().build();
    }
}
