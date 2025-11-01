package com.spartaecommerce.order.presentation.controller;

import com.spartaecommerce.common.domain.CommonResponse;
import com.spartaecommerce.common.domain.IdResponse;
import com.spartaecommerce.order.application.OrderService;
import com.spartaecommerce.order.domain.command.OrderCreateCommand;
import com.spartaecommerce.order.presentation.controller.dto.request.OrderCreateRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/orders")
    public ResponseEntity<CommonResponse<IdResponse>> create(
        @Valid @RequestBody OrderCreateRequest createRequest
    ) {
        OrderCreateCommand createCommand = createRequest.toCommand();

        Long orderId = orderService.create(createCommand);

        return ResponseEntity
            .created(URI.create("/api/v1/orders/" + orderId))
            .body(CommonResponse.create(orderId));
    }
}
