package com.sprata.sparta_ecommerce.controller;

import com.sprata.sparta_ecommerce.controller.mapper.OrderMapper;
import com.sprata.sparta_ecommerce.dto.ChangeOrderStatusRequestDto;
import com.sprata.sparta_ecommerce.dto.OrderRequestDto;
import com.sprata.sparta_ecommerce.dto.OrderResponseDto;
import com.sprata.sparta_ecommerce.dto.param.PageDto;
import com.sprata.sparta_ecommerce.dto.param.SearchOrderDto;
import com.sprata.sparta_ecommerce.entity.OrderStatus;
import com.sprata.sparta_ecommerce.service.OrderService;
import com.sprata.sparta_ecommerce.service.dto.OrderServiceSearchDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import com.sprata.sparta_ecommerce.dto.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final OrderMapper orderMapper;

    @PostMapping
    public ResponseEntity<ResponseDto<?>> createOrder(@Valid @RequestBody OrderRequestDto orderRequestDto) {
        OrderResponseDto responseDto = orderService.createOrder(orderRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseDto.success(responseDto, "주문 생성 성공"));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ResponseDto<?>> getOrdersByUserId(@PathVariable Long userId
                                                        , @ModelAttribute SearchOrderDto searchDto
                                                        , @ModelAttribute PageDto pageDto) {
        OrderServiceSearchDto serviceSearchDto = orderMapper.toServiceSearchDto(userId, searchDto);
        List<OrderResponseDto> responseDtos = orderService.getOrdersByUserId(serviceSearchDto, pageDto);
        return ResponseEntity.ok(ResponseDto.success(responseDtos, "주문 목록 조회 성공"));
    }

    @PutMapping("/{orderId}/status")
    public ResponseEntity<ResponseDto<?>> updateOrderStatus(@PathVariable Long orderId,
                                                            @Valid @RequestBody ChangeOrderStatusRequestDto requestDto
                                                            ) {
        OrderStatus status = OrderStatus.find(requestDto);
        OrderResponseDto responseDto = orderService.updateOrderStatus(orderId, status);
        return ResponseEntity.ok(ResponseDto.success(responseDto, "주문 상태 수정 성공"));
    }

    @PostMapping("/{orderId}/cancel")
    public ResponseEntity<ResponseDto<?>> cancelOrder(@PathVariable Long orderId) {
        Long id = orderService.cancelOrder(orderId);
        return ResponseEntity.ok(ResponseDto.success(id, "주문 취소 성공"));
    }
}
