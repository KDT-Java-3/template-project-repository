package com.wodydtns.commerce.domain.order.service;

import java.util.List;

import com.wodydtns.commerce.domain.order.dto.SearchOrdersProductsResponse;
import com.wodydtns.commerce.global.enums.OrderStatus;

public interface OrdersProductsService {

    void updateOrdersProducts(Long ordersProductsId, OrderStatus newStatus);

    void deleteOrdersProducts(Long ordersProductsId);

    List<SearchOrdersProductsResponse> searchOrdersProductsByMemberId(Long memberId);
}
