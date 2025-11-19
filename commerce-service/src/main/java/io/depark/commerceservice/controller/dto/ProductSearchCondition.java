package io.depark.commerceservice.controller.dto;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class ProductSearchCondition {

    private Long categoryId;

    private String name;

    private BigDecimal minPrice;

    private BigDecimal maxPrice;

    private Boolean includeZeroStock;

    private String sortBy;

    private String sortDirection;
}
