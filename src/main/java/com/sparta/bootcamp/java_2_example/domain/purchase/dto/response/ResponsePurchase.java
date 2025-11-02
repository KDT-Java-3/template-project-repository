package com.sparta.bootcamp.java_2_example.domain.purchase.dto.response;

import com.sparta.bootcamp.java_2_example.domain.purchase.entity.PurchaseProduct;
import com.sparta.bootcamp.java_2_example.domain.user.entity.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * @author : leeyounggyo
 * @package : com.sparta.bootcamp.java_2_example.domain.purchase.dto.request
 * @since : 2025. 11. 2.
 */
@Builder
@Getter
@AllArgsConstructor
public class ResponsePurchase {

	private Long purchaseId;

	private String email;

	private Long productId;

	private Integer quantity;

	private String shippingAddress;

	public static ResponsePurchase of(PurchaseProduct purchaseProduct) {
		return ResponsePurchase.builder()
			.purchaseId(purchaseProduct.getId())
			.email(purchaseProduct.getPurchase().getUser().getEmail())
			.productId(purchaseProduct.getProduct().getId())
			.quantity(purchaseProduct.getQuantity())
			.shippingAddress(purchaseProduct.getShippingAddress())
			.build();
	}

}
