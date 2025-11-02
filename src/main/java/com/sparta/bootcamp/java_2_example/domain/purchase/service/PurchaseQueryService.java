package com.sparta.bootcamp.java_2_example.domain.purchase.service;

import java.util.List;

import com.sparta.bootcamp.java_2_example.domain.purchase.dto.response.ResponsePurchase;

/**
 * @author : leeyounggyo
 * @package : com.sparta.bootcamp.java_2_example.domain.purchase.service
 * @since : 2025. 11. 2.
 */
public interface PurchaseQueryService {

	List<ResponsePurchase> getPurchaseByUser(String eamil);

}
