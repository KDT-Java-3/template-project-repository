package com.sparta.project1.domain.order.service;

import com.sparta.project1.domain.order.api.dto.OrderRequest;
import com.sparta.project1.domain.order.api.dto.ProductOrderRequest;
import com.sparta.project1.domain.order.domain.Orders;
import com.sparta.project1.domain.order.domain.ProductOrder;
import com.sparta.project1.domain.order.domain.ProductOrderInfoEvent;
import com.sparta.project1.domain.order.repository.OrdersRepository;
import com.sparta.project1.domain.order.repository.ProductOrderRepository;
import com.sparta.project1.domain.product.domain.Product;
import com.sparta.project1.domain.product.domain.ProductOrderInfo;
import com.sparta.project1.domain.product.service.ProductFindService;
import com.sparta.project1.domain.user.domain.Users;
import com.sparta.project1.domain.user.service.UsersFindService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderModifyService {
    private final OrdersRepository ordersRepository;
    private final ProductOrderRepository productOrderRepository;
    private final ProductFindService productFindService;
    private final UsersFindService usersFindService;
    private final ApplicationEventPublisher eventPublisher;

    public void order(OrderRequest request) {
        List<ProductOrderRequest> productRequest = request.products();
        List<Product> products = productFindService.getAllByIds(productRequest
                .stream().map(ProductOrderRequest::productId).toList());

        productRequest.sort(Comparator.comparing(ProductOrderRequest::productId));
        products.sort(Comparator.comparing(Product::getId));

        // 재고가 충분한지 확인
        List<ProductOrderInfo> productOrderInfos = new ArrayList<>();
        for (int i = 0; i < productRequest.size(); i++) {
            ProductOrderRequest productOrderRequest = productRequest.get(i);
            Product product = products.get(i);
            product.checkStockRemaining(productOrderRequest.quantity());
            ProductOrderInfo productOrderInfo = new ProductOrderInfo(product, productOrderRequest.quantity());
            productOrderInfos.add(productOrderInfo);
        }

        Users user = usersFindService.getById(request.userId());

        Orders orders = Orders.register(user, request.shippingAddress(), productOrderInfos);
        orders = ordersRepository.save(orders);

        List<ProductOrder> productOrders = new ArrayList<>();
        for (int i = 0; i < productRequest.size(); i++) {
            ProductOrderRequest productOrderRequest = productRequest.get(i);
            Product product = products.get(i);
            ProductOrder productOrder = ProductOrder.generate(product, orders, productOrderRequest.quantity());
            productOrders.add(productOrder);
        }

        productOrderRepository.saveAll(productOrders);
        // product quantity 빼기
        eventPublisher.publishEvent(new ProductOrderInfoEvent(productOrderInfos));
    }

    public void changeStatus(Long orderId, String status) {
        Orders orders = ordersRepository.findById(orderId)
                .orElseThrow(() -> new NoSuchElementException("order not found"));
        orders.changeStatus(status);
        ordersRepository.save(orders);
    }

    public void cancelOrder(Long userId, Long orderId) {
        Orders orders = ordersRepository.findByOrderIdAndUserId(orderId, userId)
                        .orElseThrow(() -> new NoSuchElementException("order not found"));
        orders.cancelOrder();
        ordersRepository.save(orders);
    }
}
