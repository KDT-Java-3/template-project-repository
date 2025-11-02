package com.sparta.bootcamp.java_2_example.domain.purchase.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.bootcamp.java_2_example.common.enums.PurchaseStatus;
import com.sparta.bootcamp.java_2_example.domain.purchase.dto.request.RequestPurchase;
import com.sparta.bootcamp.java_2_example.domain.purchase.dto.response.ResponsePurchase;
import com.sparta.bootcamp.java_2_example.domain.purchase.service.PurchaseCommandService;
import com.sparta.bootcamp.java_2_example.domain.purchase.service.PurchaseQueryService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author : leeyounggyo
 * @package : com.sparta.bootcamp.java_2_example.domain.purchase.controller
 * @since : 2025. 11. 2.
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/purchase")
public class PurchaseController {

	private final PurchaseQueryService purchaseQueryService;
	private final PurchaseCommandService purchaseCommandService;

	@PostMapping
	public ResponseEntity<ResponsePurchase> createPurchase(

		@Valid
		@RequestBody
		RequestPurchase request

	) {
		return ResponseEntity.ok(purchaseCommandService.createPurchase(request));
	}

	@GetMapping("/user/{userId}")
	public ResponseEntity<List<ResponsePurchase>> getPurchaseByUser(

		@PathVariable
		Long userId

	) {
		return ResponseEntity.ok(purchaseQueryService.getPurchaseByUser(userId));
	}

	@PatchMapping("/{purchaseId}/status")
	public ResponseEntity<ResponsePurchase> updatePurchaseStatus(

		@PathVariable
		Long purchaseId,

		@RequestParam
		PurchaseStatus status

	) {

		return ResponseEntity.ok(purchaseCommandService.updatePurchaseStatus(purchaseId, status));
	}

	@PostMapping("/{purchaseId}/cancel")
	public ResponseEntity<ResponsePurchase> cancelPurchase(

		@PathVariable
		Long purchaseId

	) {
		return ResponseEntity.ok(purchaseCommandService.cancelPurchase(purchaseId));
	}

}
