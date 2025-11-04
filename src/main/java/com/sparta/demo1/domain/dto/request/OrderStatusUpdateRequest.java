package com.sparta.demo1.domain.dto.request;

import com.sparta.demo1.domain.entity.OrderStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderStatusUpdateRequest {

  @NotNull
  private OrderStatus status;
}