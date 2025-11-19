package io.depark.commerceservice.controller;

import io.depark.commerceservice.common.ApiResponse;
import io.depark.commerceservice.controller.dto.*;
import io.depark.commerceservice.entity.enums.RefundStatus;
import io.depark.commerceservice.service.RefundService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/refunds")
@RequiredArgsConstructor
public class RefundController {

    private final RefundService refundService;

    @GetMapping("/{id}")
    public ApiResponse<RefundDetailResponse> getRefund(@PathVariable Long id) {
        return ApiResponse.success(refundService.getRefund(id));
    }

    @GetMapping
    public ApiResponse<Page<RefundResponse>> searchRefunds(
            @RequestParam(required = false) RefundStatus status,
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate,
            @PageableDefault(size = 5, sort = "id", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        RefundSearchCondition condition = RefundSearchCondition.builder()
                .status(status)
                .startDate(startDate)
                .endDate(endDate)
                .build();
        return ApiResponse.success(refundService.searchRefunds(condition, pageable));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<RefundResponse>> create(@Valid @RequestBody RefundCreateRequest request) {
        return ApiResponse.created(refundService.create(request));
    }

    @PostMapping("/{id}/process")
    public ApiResponse<RefundResponse> process(
            @PathVariable Long id,
            @Valid @RequestBody RefundProcessRequest request
    ) {
        return ApiResponse.success(refundService.process(id, request));
    }
}
