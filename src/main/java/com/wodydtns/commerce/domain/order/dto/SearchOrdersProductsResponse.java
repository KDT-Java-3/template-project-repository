package com.wodydtns.commerce.domain.order.dto;

import java.time.LocalDateTime;

import com.wodydtns.commerce.domain.product.dto.ProductDto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SearchOrdersProductsResponse {

    private String orderStatus;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private ProductDto product;

}
