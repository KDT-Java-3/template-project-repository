package com.sparta.work.order.dto.response;

import com.sparta.work.product.domain.Product;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ResponseOrderDto {

    private Long id;
    private String status;
    private Product product;
    private LocalDateTime createAt;

}
