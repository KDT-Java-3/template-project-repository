package com.sparta.bootcamp.java_2_example.domain.product.dto.search;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author : leeyounggyo
 * @package : com.sparta.bootcamp.java_2_example.domain.category.dto.request
 * @since : 2025. 11. 2.
 */
@Getter
@Setter
@AllArgsConstructor
public class SearchProduct {

	private Long categoryId;

	private BigDecimal from;

	private BigDecimal to;

	private String productName;

}
