package com.sparta.work.order.service;

import com.sparta.work.order.domain.Order;
import com.sparta.work.order.domain.OrdersRepository;
import com.sparta.work.order.dto.response.ResponseOrderDto;
import com.sparta.work.order.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrdersRepository ordersRepository;
    private final OrderMapper OrderMapper;

    public List<ResponseOrderDto> findAllOrder(){
        List<Order> orders = ordersRepository.findAll();

        return orders.stream().map(OrderMapper::toResponseDto).toList();
    }

}
