package com.sparta.commerce.management.dto.request.purchase;

import io.smallrye.common.constraint.NotNull;
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
public class PurchaseUpdateRequest {

    @NotNull
    UUID id;
    @NotNull
    String status;

}
