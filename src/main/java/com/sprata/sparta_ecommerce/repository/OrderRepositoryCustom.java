package com.sprata.sparta_ecommerce.repository;

import com.sprata.sparta_ecommerce.dto.param.PageDto;
import com.sprata.sparta_ecommerce.entity.Order;
import com.sprata.sparta_ecommerce.service.dto.OrderServiceSearchDto;

import java.util.Collection;
import java.util.List;

public interface OrderRepositoryCustom {
    List<Order> findByProductWhenComplete(Long productId);

    List<Order> findByUserWithPaging(OrderServiceSearchDto searchDto, PageDto pageDto);
}
