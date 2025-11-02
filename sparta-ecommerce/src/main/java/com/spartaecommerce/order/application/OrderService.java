package com.spartaecommerce.order.application;

import com.spartaecommerce.common.exception.BusinessException;
import com.spartaecommerce.common.exception.ErrorCode;
import com.spartaecommerce.order.application.dto.OrderInfo;
import com.spartaecommerce.order.domain.command.OrderCreateCommand;
import com.spartaecommerce.order.domain.command.OrderStatusUpdateCommand;
import com.spartaecommerce.order.domain.entity.Order;
import com.spartaecommerce.order.domain.entity.OrderItem;
import com.spartaecommerce.order.domain.entity.OrderStatus;
import com.spartaecommerce.order.domain.query.OrderSearchQuery;
import com.spartaecommerce.order.domain.repository.OrderRepository;
import com.spartaecommerce.product.domain.entity.Product;
import com.spartaecommerce.product.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Transactional
    public Long create(OrderCreateCommand createCommand) {
        // TODO: userId에 해당하는 사용자 있는지 체크
        Product product = productRepository.getById(createCommand.productId());
        product.deductQuantity(createCommand.quantity());
        productRepository.save(product);

        Order order = Order.createNew(createCommand);
        order.addOrderItem(
            product.getProductId(),
            product.getName(),
            product.getPrice(),
            createCommand.quantity()
        );

        return orderRepository.save(order);
    }

    @Transactional
    public void updateOrderStatus(OrderStatusUpdateCommand updateCommand) {
        if (updateCommand.orderStatus() == OrderStatus.CANCELED) {
            cancel(updateCommand.orderId());
            return;
        }

        Order order = orderRepository.getById(updateCommand.orderId());
        order.updateOrderStatus(updateCommand.orderStatus());
        orderRepository.save(order);
    }

    @Transactional
    public void cancel(Long orderId) {
        Order order = orderRepository.getById(orderId);
        order.cancel();

        restoreProductStock(order);

        orderRepository.save(order);
    }

    public List<OrderInfo> search(OrderSearchQuery searchQuery) {
        List<Order> orders = orderRepository.search(searchQuery);
        return orders.stream()
            .map(OrderInfo::from)
            .toList();
    }

    private void restoreProductStock(Order order) {
        List<Long> productIds = order.getOrderItems().stream()
            .map(OrderItem::getProductId)
            .toList();

        List<Product> products = productRepository.findAllByIdIn(productIds);

        Map<Long, Product> productMap = products.stream()
            .collect(Collectors.toMap(Product::getProductId, Function.identity()));

        order.getOrderItems().forEach(orderItem -> {
            Product product = productMap.get(orderItem.getProductId());

            if (product == null) {
                throw new BusinessException(
                    ErrorCode.ENTITY_NOT_FOUND,
                    "Product not found: " + orderItem.getProductId()
                );
            }

            product.restoreQuantity(orderItem.getQuantity());
            productRepository.save(product);
        });
    }
}