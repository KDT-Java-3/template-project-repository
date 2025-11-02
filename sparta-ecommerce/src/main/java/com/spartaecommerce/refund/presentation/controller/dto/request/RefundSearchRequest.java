package com.spartaecommerce.refund.presentation.controller.dto.request;

import com.spartaecommerce.refund.domain.query.RefundSearchQuery;
import jakarta.validation.constraints.NotNull;

public record RefundSearchRequest(
    @NotNull
    Long userId
) {
    public RefundSearchQuery toQuery() {
        return new RefundSearchQuery(userId);
    }
}
