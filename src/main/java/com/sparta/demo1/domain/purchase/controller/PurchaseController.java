package com.sparta.demo1.domain.purchase.controller;

import com.sparta.demo1.common.model.ApiResponseModel;
import com.sparta.demo1.domain.product.dto.request.ProductReqDto;
import com.sparta.demo1.domain.product.dto.response.ProductResDto;
import com.sparta.demo1.domain.product.service.ProductService;
import com.sparta.demo1.domain.purchase.dto.request.PurchaseReqDto;
import com.sparta.demo1.domain.purchase.dto.response.PurchaseResDto;
import com.sparta.demo1.domain.purchase.enums.PurchaseOrderBy;
import com.sparta.demo1.domain.purchase.enums.PurchaseStatus;
import com.sparta.demo1.domain.purchase.service.PurchaseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/purchase")
public class PurchaseController {

    private final PurchaseService purchaseService;

    @GetMapping("/{id}")
    public ApiResponseModel<PurchaseResDto.PurchaseDetailInfo> purchase(@PathVariable Long id){
        return new ApiResponseModel<>(purchaseService.getPurchaseDetailInfo(id));
    }

    @PostMapping("/filter")
    public ApiResponseModel<Page<PurchaseResDto.PurchaseInfo>> getFilterProductAll(
            @Valid @RequestBody PurchaseReqDto.PurchaseFilterDto purchaseFilterDto,
            Pageable pageable
    ) {
        Page<PurchaseResDto.PurchaseInfo> result = purchaseService.findPurchaseFilter(
                purchaseFilterDto.getUserId(),
                purchaseFilterDto.getPurchaseStatus(),
                purchaseFilterDto.getStartDate(),
                purchaseFilterDto.getEndDate(),
                pageable,
                purchaseFilterDto.getOrderByList()
        );

        return new ApiResponseModel<>(result);
    }

    @PostMapping("/create")
    public ApiResponseModel<Long> create(@Valid @RequestBody PurchaseReqDto.PurchaseCreateDto purchaseCreateDto) {
        return new ApiResponseModel<>(purchaseService.createPurchase(purchaseCreateDto.getUserId(), purchaseCreateDto.getShippingAddress(), purchaseCreateDto.getPurchaseCreateProductInfoList()));
    }

    @DeleteMapping("/{id}")
    public ApiResponseModel<Boolean> deletePurchase(@PathVariable Long id) {
        purchaseService.deletePurchase(id);
        return new ApiResponseModel<>(true);
    }
}
