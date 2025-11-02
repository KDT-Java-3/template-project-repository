package com.sparta.bootcamp.java_2_example.domain.product.dto.response;

import java.math.BigDecimal;

import com.sparta.bootcamp.java_2_example.domain.product.entity.Product;

import lombok.Builder;
import lombok.Getter;

/**
 * @author : leeyounggyo
 * @package : com.sparta.bootcamp.java_2_example.domain.category.dto.request
 * @since : 2025. 11. 2.
 */
@Builder
@Getter
public class ResponseProduct {

	private Long id;

	private Long categoryId;

	private String name;

	private BigDecimal price;

	private Integer stock;

	public static ResponseProduct of(Product product) {
		return ResponseProduct.builder()
			.id(product.getId())
			.categoryId(product.getCategory().getId())
			.name(product.getName())
			.price(product.getPrice())
			.stock(product.getStock())
			.build();
	}

}
