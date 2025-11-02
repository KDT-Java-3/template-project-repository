package com.wodydtns.commerce.domain.order.service.impl;

import com.wodydtns.commerce.domain.order.dto.SearchOrdersProductsResponse;
import com.wodydtns.commerce.domain.order.entity.OrdersProducts;
import com.wodydtns.commerce.domain.order.repository.OrdersProductsRepository;
import com.wodydtns.commerce.domain.order.service.OrdersProductsService;
import com.wodydtns.commerce.domain.product.dto.ProductDto;
import com.wodydtns.commerce.global.enums.OrderStatus;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrdersProductsServiceImpl implements OrdersProductsService {

        private final OrdersProductsRepository ordersProductsRepository;

        @Override
        @Transactional
        public void updateOrdersProducts(Long ordersProductsId, OrderStatus newStatus) {
                OrdersProducts ordersProducts = ordersProductsRepository
                                .findByIdAndOrderStatus(ordersProductsId, OrderStatus.PENDING)
                                .orElseThrow(() -> new RuntimeException(
                                                "PENDING 상태의 주문 상품을 찾을 수 없습니다: " + ordersProductsId));

                ordersProducts.updateOrderStatus(newStatus);
        }

        @Override
        @Transactional
        public void deleteOrdersProducts(Long ordersProductsId) {
                OrdersProducts ordersProducts = ordersProductsRepository
                                .findByIdAndOrderStatus(ordersProductsId, OrderStatus.PENDING)
                                .orElseThrow(() -> new RuntimeException(
                                                "PENDING 상태의 주문 상품을 찾을 수 없습니다: " + ordersProductsId));

                ordersProductsRepository.delete(ordersProducts);
        }

        @Override
        public List<SearchOrdersProductsResponse> searchOrdersProductsByMemberId(Long memberId) {
                List<OrdersProducts> ordersProductsList = ordersProductsRepository.findByMemberId(memberId);
                return ordersProductsList.stream()
                                .map(ordersProducts -> SearchOrdersProductsResponse.builder()
                                                .orderStatus(ordersProducts.getOrderStatus().name())
                                                .createdAt(ordersProducts.getCreatedAt())
                                                .updatedAt(ordersProducts.getUpdatedAt())
                                                .product(ProductDto.builder()
                                                                .name(ordersProducts.getProduct().getName())
                                                                .price(ordersProducts.getProduct().getPrice())
                                                                .stock(ordersProducts.getProduct().getStock())
                                                                .description(ordersProducts.getProduct()
                                                                                .getDescription())
                                                                .categoryId(ordersProducts.getProduct().getCategoryId())
                                                                .build())
                                                .build())
                                .collect(Collectors.toList());
        }
}
