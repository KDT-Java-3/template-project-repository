package io.depark.commerceservice.controller;

import io.depark.commerceservice.common.ApiResponse;
import io.depark.commerceservice.controller.dto.PurchaseDetailResponse;
import io.depark.commerceservice.controller.dto.PurchaseRequest;
import io.depark.commerceservice.controller.dto.PurchaseResponse;
import io.depark.commerceservice.controller.dto.PurchaseSearchCondition;
import io.depark.commerceservice.entity.enums.PurchaseStatus;
import io.depark.commerceservice.service.PurchaseService;
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
@RequestMapping("/api/purchases")
@RequiredArgsConstructor
public class PurchaseController {

    private final PurchaseService purchaseService;

    @GetMapping("/{id}")
    public ApiResponse<PurchaseDetailResponse> getPurchase(@PathVariable Long id){
        return ApiResponse.success(purchaseService.getPurchase(id));
    }

    @GetMapping
    public ApiResponse<Page<PurchaseResponse>> searchPurchases(
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) PurchaseStatus status,
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate,
            @PageableDefault(size = 5, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        PurchaseSearchCondition condition = PurchaseSearchCondition.builder()
                .userId(userId)
                .status(status)
                .startDate(startDate)
                .endDate(endDate)
                .build();
        return ApiResponse.success(purchaseService.searchPurchases(condition, pageable));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<PurchaseResponse>> create(@Valid @RequestBody PurchaseRequest request) {
        return ApiResponse.created(purchaseService.create(request));
    }

    @PostMapping("/{id}/cancel")
    public ApiResponse<Void> cancel(@PathVariable Long id) {
        purchaseService.cancel(id);
        return ApiResponse.success();
    }
}
