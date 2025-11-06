package com.sparta.commerce.management.dto.request.purchase;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PurchaseCreateRequest {

    //생성
    @NotNull(message = "주문자는 필수입니다.")
    UUID userId;

    @NotNull(message = "배달 장소는 필수입니다.")
    String recipientAddress;

    @NotNull(message = "주문 상품은 필수 입니다.")
    List<PurchaseProductRequest> purchaseProductRequests;

}
