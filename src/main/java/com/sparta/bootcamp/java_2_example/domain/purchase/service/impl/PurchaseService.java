package com.sparta.bootcamp.java_2_example.domain.purchase.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sparta.bootcamp.java_2_example.common.enums.PurchaseStatus;
import com.sparta.bootcamp.java_2_example.domain.purchase.dto.request.RequestPurchase;
import com.sparta.bootcamp.java_2_example.domain.purchase.dto.response.ResponsePurchase;
import com.sparta.bootcamp.java_2_example.domain.purchase.repository.PurchaseProductRepository;
import com.sparta.bootcamp.java_2_example.domain.purchase.repository.PurchaseRepository;
import com.sparta.bootcamp.java_2_example.domain.purchase.service.PurchaseCommandService;
import com.sparta.bootcamp.java_2_example.domain.purchase.service.PurchaseQueryService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author : leeyounggyo
 * @package : com.sparta.bootcamp.java_2_example.domain.purchase.service
 * @since : 2025. 11. 2.
 */
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PurchaseService implements PurchaseQueryService, PurchaseCommandService {

	private final PurchaseProductRepository purchaseProductRepository;
	private final PurchaseRepository purchaseRepository;

	@Override
	public ResponsePurchase createPurchase(RequestPurchase request) {
		return null;
	}

	@Override
	public ResponsePurchase updatePurchaseStatus(Long purchaseId, PurchaseStatus status) {
		return null;
	}

	@Override
	public ResponsePurchase cancelPurchase(Long purchaseId) {
		return null;
	}

	@Override
	public List<ResponsePurchase> getPurchaseByUser(Long userId) {
		return List.of();
	}

}
