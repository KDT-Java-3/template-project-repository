package com.example.week01_project.web;


import com.example.week01_project.dto.refund.RefundRequest;
import com.example.week01_project.domain.refund.Refund;
import com.example.week01_project.service.RefundService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/refunds")
public class RefundController {
    private final RefundService service;
    public RefundController(RefundService service) { this.service = service; }

    @PostMapping
    public Long request(@RequestBody @Valid RefundRequest req){ return service.request(req); }

    @PostMapping("/{id}/approve")
    public void approve(@PathVariable Long id){ service.approve(id); }

    @PostMapping("/{id}/reject")
    public void reject(@PathVariable Long id){ service.reject(id); }

    @GetMapping
    public Page<Refund> search(@RequestParam(required = false) Long userId,
                               @RequestParam(required = false) Refund.Status status,
                               Pageable pageable){
        return service.search(userId, status, pageable);
    }
}
