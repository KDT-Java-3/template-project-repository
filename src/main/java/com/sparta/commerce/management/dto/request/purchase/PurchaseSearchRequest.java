package com.sparta.commerce.management.dto.request.purchase;

import com.sparta.commerce.management.entity.Product;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PurchaseSearchRequest {

    UUID userId; //주문자ID

}
