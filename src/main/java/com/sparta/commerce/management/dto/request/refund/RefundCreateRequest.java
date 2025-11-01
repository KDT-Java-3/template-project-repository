package com.sparta.commerce.management.dto.request.refund;

import io.smallrye.common.constraint.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RefundCreateRequest {

    @NotNull
    UUID purchaseId;
    @NotNull
    String reason;

}
