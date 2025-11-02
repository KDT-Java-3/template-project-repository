package com.sparta.bootcamp.java_2_example.domain.product.dto.request;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author : leeyounggyo
 * @package : com.sparta.bootcamp.java_2_example.domain.category.dto.request
 * @since : 2025. 11. 2.
 */
@Getter
@AllArgsConstructor
public class RequestCreateProduct {

	private Long categoryId;

	private String productName;

	private String description;

	private BigDecimal price;

	private Integer stock;

}
