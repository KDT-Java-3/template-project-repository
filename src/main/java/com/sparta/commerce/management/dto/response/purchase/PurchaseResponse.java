package com.sparta.commerce.management.dto.response.purchase;

import com.sparta.commerce.management.dto.request.purchase.PurchaseCreateRequest;
import com.sparta.commerce.management.entity.Purchase;
import com.sparta.commerce.management.entity.PurchaseProduct;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PurchaseResponse {

    UUID id;
    String userId;
    Long totalCount;
    BigDecimal totalPrice;
    String status;
    String recipientAddress;
    String recipientName;
    Instant rgDt;
    List<PurchaseProduct> purchaseProducts;

    public static PurchaseResponse getPurchase(Purchase purchase){
        return PurchaseResponse.builder()
                .id(purchase.getId())
                .userId(purchase.getUser().getId())
                .totalCount(purchase.getTotalCount())
                .totalPrice(purchase.getTotalPrice())
                .status(purchase.getStatus())
                .recipientAddress(purchase.getRecipientAddress())
                .recipientName(purchase.getRecipientName())
                .rgDt(purchase.getRgDt())
                .purchaseProducts(purchase.getPurchaseProducts())
                .build();
    }

    public static List<PurchaseResponse> getPurchaseList(List<Purchase> purchaseList){
        return purchaseList.stream().map(PurchaseResponse::getPurchase).toList();
    }
}
