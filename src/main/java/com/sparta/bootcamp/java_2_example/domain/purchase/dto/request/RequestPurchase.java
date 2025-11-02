package com.sparta.bootcamp.java_2_example.domain.purchase.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author : leeyounggyo
 * @package : com.sparta.bootcamp.java_2_example.domain.purchase.dto.request
 * @since : 2025. 11. 2.
 */
@Getter
@AllArgsConstructor
public class RequestPurchase {

	private String email;

	private Long productId;

	private Integer quantity;

	private String shippingAddress;

}
