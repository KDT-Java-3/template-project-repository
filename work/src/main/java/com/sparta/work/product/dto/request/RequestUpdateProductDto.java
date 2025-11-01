package com.sparta.work.product.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestUpdateProductDto {
    private String name;
    private String description;
    private Integer price;
    private Integer stock;
    private Long categoryId;
}
