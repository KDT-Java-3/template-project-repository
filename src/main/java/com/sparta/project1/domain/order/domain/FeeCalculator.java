package com.sparta.project1.domain.order.domain;

import com.sparta.project1.domain.product.domain.ProductOrderInfo;

import java.math.BigDecimal;
import java.util.List;

public class FeeCalculator {

    public static BigDecimal calculateTotalPrice(List<ProductOrderInfo> productOrderInfos) {
        return productOrderInfos.stream()
                .map(p -> p.product()
                        .getPrice().multiply(new BigDecimal(p.quantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

    }
}
