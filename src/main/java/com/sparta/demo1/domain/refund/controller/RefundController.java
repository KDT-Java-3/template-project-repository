package com.sparta.demo1.domain.refund.controller;

import com.sparta.demo1.common.model.ApiResponseModel;
import com.sparta.demo1.domain.purchase.dto.request.PurchaseReqDto;
import com.sparta.demo1.domain.purchase.dto.response.PurchaseResDto;
import com.sparta.demo1.domain.purchase.service.PurchaseService;
import com.sparta.demo1.domain.refund.dto.request.RefundReqDto;
import com.sparta.demo1.domain.refund.dto.response.RefundResDto;
import com.sparta.demo1.domain.refund.service.RefundService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/refund")
public class RefundController {

    private final RefundService refundService;

    @PostMapping("/admin/filter")
    public ApiResponseModel<Page<RefundResDto.RefundInfo>> getRefundAdminFiltering(@Valid @RequestBody RefundReqDto.RefundFilterDto filterDto, Pageable pageable){
        return new ApiResponseModel<>(refundService.getRefundAdminFilter(filterDto.getRefundStatus(), pageable));
    }

    @PostMapping("/filter")
    public ApiResponseModel<Page<RefundResDto.RefundInfo>> getRefundFiltering(@Valid @RequestBody RefundReqDto.RefundFilterDto filterDto, Pageable pageable){
        return new ApiResponseModel<>(refundService.getRefundFilter(filterDto.getUserId(), filterDto.getRefundStatus(), pageable));
    }

    @PostMapping("/create")
    public ApiResponseModel<Long> create(@Valid @RequestBody RefundReqDto.RefundCreateDto refundCreateDto) {
        return new ApiResponseModel<>(refundService.createRefund(refundCreateDto.getUserId(), refundCreateDto.getPurchaseId(), refundCreateDto.getReason()));
    }

    @PostMapping("/processing/approve")
    public ApiResponseModel<Boolean> refundApprove(@Valid @RequestBody RefundReqDto.RefundProcessing refundProcessing) {
        refundService.refundApprovedProcessing(refundProcessing.getUserId(), refundProcessing.getPurchaseId());
        return new ApiResponseModel<>(true);
    }

    @PostMapping("/processing/reject")
    public ApiResponseModel<Boolean> refundReject(@Valid @RequestBody RefundReqDto.RefundProcessing refundProcessing) {
        refundService.refundRejectedProcessing(refundProcessing.getUserId(), refundProcessing.getPurchaseId());
        return new ApiResponseModel<>(true);
    }
}
