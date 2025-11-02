package com.sparta.project1.domain.order.domain;

import com.sparta.project1.domain.product.domain.ProductOrderInfo;

import java.util.List;

public record ProductOrderInfoEvent(List<ProductOrderInfo> productOrderInfos) {
}
