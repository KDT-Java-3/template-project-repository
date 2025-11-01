package com.sparta.restful_1week.domain.order.service;

import com.sparta.restful_1week.domain.order.dto.OrderInDTO;
import com.sparta.restful_1week.domain.order.dto.OrderOutDTO;
import com.sparta.restful_1week.domain.order.entity.Order;
import com.sparta.restful_1week.domain.product.dto.ProductInDTO;
import com.sparta.restful_1week.domain.product.dto.ProductOutDTO;
import com.sparta.restful_1week.domain.product.entity.Product;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderService {
    private final Map<Long, Order> orderMap = new HashMap<>();

    public OrderOutDTO createOrder(OrderInDTO orderInDTO) {

        // inDto -> entity
        Order order = new Order(orderInDTO);

        // category max id chk
        Long maxId = orderMap.size() > 0 ? Collections.max(orderMap.keySet()) + 1 : 1;
        order.setId(maxId);

        // db 저장
        orderMap.put(order.getId(), order);

        // entity -> outDto
        OrderOutDTO orderOutDTO = new OrderOutDTO(order);

        return orderOutDTO;
    }

    public List<OrderOutDTO> getOrders() {
        // Map to list
        List<OrderOutDTO> orderOutDTOList = orderMap.values().stream()
                .map(OrderOutDTO::new).toList();

        return orderOutDTOList;
    }

    public OrderOutDTO updateOrder(Long id, OrderInDTO orderInDTO) {
        // 해당 메모가 DB에 존재하는지 확인
        if(orderMap.containsKey(id)) {
            // 해당 ID로 카테고리 가져오기
            Order order = orderMap.get(id);

            // 메모 수정
            order.updateOrder(orderInDTO);

            // entity -> outDto
            OrderOutDTO orderOutDTO = new OrderOutDTO(order);

            return orderOutDTO;

        } else {
            throw new IllegalArgumentException("선택한 상품 데이터는 존재하지 않습니다.");
        }
    }
}
