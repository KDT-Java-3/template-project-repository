package com.sparta.work.product.dto.response;

import com.sparta.work.category.domain.Category;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseProductDto {
    private Long id;
    private String name;
    private String description;
    private Integer price;
    private Integer stock;
    private Category category;
}
