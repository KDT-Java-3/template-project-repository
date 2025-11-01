package com.spartaecommerce.order.application;

import com.spartaecommerce.common.util.DateTimeHolder;
import com.spartaecommerce.order.domain.command.OrderCreateCommand;
import com.spartaecommerce.order.domain.entity.Order;
import com.spartaecommerce.order.domain.repository.OrderRepository;
import com.spartaecommerce.product.domain.entity.Product;
import com.spartaecommerce.product.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final DateTimeHolder dateTimeHolder;

    @Transactional
    public Long create(OrderCreateCommand createCommand) {
        // TODO: userId에 해당하는 사용자 있는지 체크
        Product product = productRepository.getById(createCommand.productId());
        product.deductQuantity(createCommand.quantity());
        productRepository.save(product);

        Order order = Order.createNew(createCommand, dateTimeHolder);
        order.addOrderItem(product.getProductId(), product.getPrice(), createCommand.quantity());

        return orderRepository.save(order);
    }
}