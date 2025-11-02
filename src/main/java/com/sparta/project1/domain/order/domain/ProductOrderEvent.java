package com.sparta.project1.domain.order.domain;

import java.util.List;

public record ProductOrderEvent(List<ProductOrder> productOrders) {
}
