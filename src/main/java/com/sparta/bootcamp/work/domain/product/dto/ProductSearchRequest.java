package com.sparta.bootcamp.work.domain.product.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
public class ProductSearchRequest {

    private String name;

    private Long categoryId;

    private double priceUpper;

    private double priceLopper;

    public BigDecimal getPriceUpper(){
        return new BigDecimal(priceUpper);
    }

    public BigDecimal getPriceLopper(){
        return new BigDecimal(priceLopper);
    }

}
