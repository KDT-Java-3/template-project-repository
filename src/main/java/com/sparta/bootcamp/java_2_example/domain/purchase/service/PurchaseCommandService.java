package com.sparta.bootcamp.java_2_example.domain.purchase.service;

import com.sparta.bootcamp.java_2_example.common.enums.PurchaseStatus;
import com.sparta.bootcamp.java_2_example.domain.purchase.dto.request.RequestPurchase;
import com.sparta.bootcamp.java_2_example.domain.purchase.dto.response.ResponsePurchase;

import jakarta.validation.Valid;

/**
 * @author : leeyounggyo
 * @package : com.sparta.bootcamp.java_2_example.domain.purchase.service
 * @since : 2025. 11. 2.
 */
public interface PurchaseCommandService {

	ResponsePurchase createPurchase(@Valid RequestPurchase request);

	ResponsePurchase updatePurchaseStatus(Long purchaseId, PurchaseStatus status);

	ResponsePurchase cancelPurchase(Long purchaseId);

}
