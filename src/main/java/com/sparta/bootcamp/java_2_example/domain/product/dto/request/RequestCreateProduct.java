package com.sparta.bootcamp.java_2_example.domain.product.dto.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

	@NotNull
	private Long categoryId;

	@NotBlank
	private String productName;

	@NotBlank
	private String description;

	@NotNull
	private BigDecimal price;

	@NotNull
	private Integer stock;

}
