package com.sparta.demo1.domain.purchase.dto.response;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sparta.demo1.domain.product.dto.response.ProductResDto;
import com.sparta.demo1.domain.purchase.enums.PurchaseStatus;
import com.sparta.demo1.domain.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

public class PurchaseResDto {

    @Getter
    @Setter
    @NoArgsConstructor
    public static class PurchaseInfo{
        private Long id;
        private BigDecimal totalPrice;
        private PurchaseStatus status;
        private String shippingAddress;

        @Builder
        public PurchaseInfo(Long id, BigDecimal totalPrice, PurchaseStatus status, String shippingAddress) {
            this.id = id;
            this.totalPrice = totalPrice;
            this.status = status;
            this.shippingAddress = shippingAddress;
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class PurchaseDetailInfo{
        private PurchaseInfo purchaseInfo;
        private List<ProductResDto.ProductInfo> productInfoList;

        @Builder
        public PurchaseDetailInfo(PurchaseInfo purchaseInfo, List<ProductResDto.ProductInfo> productInfoList) {
            this.purchaseInfo = purchaseInfo;
            this.productInfoList = productInfoList;
        }
    }
}
