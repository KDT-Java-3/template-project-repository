package com.spartaecommerce.order.presentation.controller.dto.request;

import com.spartaecommerce.order.domain.query.OrderSearchQuery;

public record OrderSearchRequest(
    Long userId
) {
    public OrderSearchQuery toQuery() {
        return new OrderSearchQuery(userId);
    }
}