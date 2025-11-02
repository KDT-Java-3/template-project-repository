package com.sparta.bootcamp.java_2_example.domain.purchase.service.impl;

import static com.sparta.bootcamp.java_2_example.domain.purchase.entity.Purchase.*;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sparta.bootcamp.java_2_example.common.enums.PurchaseStatus;
import com.sparta.bootcamp.java_2_example.domain.product.entity.Product;
import com.sparta.bootcamp.java_2_example.domain.product.repository.ProductRepository;
import com.sparta.bootcamp.java_2_example.domain.purchase.dto.request.RequestPurchase;
import com.sparta.bootcamp.java_2_example.domain.purchase.dto.response.ResponsePurchase;
import com.sparta.bootcamp.java_2_example.domain.purchase.entity.Purchase;
import com.sparta.bootcamp.java_2_example.domain.purchase.entity.PurchaseProduct;
import com.sparta.bootcamp.java_2_example.domain.purchase.repository.PurchaseProductRepository;
import com.sparta.bootcamp.java_2_example.domain.purchase.repository.PurchaseRepository;
import com.sparta.bootcamp.java_2_example.domain.purchase.service.PurchaseCommandService;
import com.sparta.bootcamp.java_2_example.domain.purchase.service.PurchaseQueryService;
import com.sparta.bootcamp.java_2_example.domain.user.entity.User;
import com.sparta.bootcamp.java_2_example.domain.user.repository.UserRepository;

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
	private final ProductRepository productRepository;
	private final UserRepository userRepository;

	@Override
	public ResponsePurchase createPurchase(RequestPurchase request) {
		// 존재하는 user 인지
		User user = userRepository.findByEmail(request.getEmail())
			.orElseThrow(() -> new IllegalArgumentException("User not found"));

		// 존재하는 product 인지
		Product product = productRepository.findById(request.getProductId())
			.orElseThrow(() -> new IllegalArgumentException("Product not found"));

		// 수량이 남았는지
		product.validateQuantity(request.getQuantity());
		product.minusStock(request.getQuantity());

		Purchase purchase = purchaseRepository.save(of(request, user, product));

		PurchaseProduct purchaseProduct = purchaseProductRepository.save(PurchaseProduct.of(request, purchase, product));

		return ResponsePurchase.of(purchaseProduct);

	}

	@Override
	public ResponsePurchase updatePurchaseStatus(Long purchaseId, PurchaseStatus status) {

		PurchaseProduct purchaseProduct = purchaseProductRepository.findById(purchaseId)
			.orElseThrow(() -> new IllegalArgumentException("Purchase not found"));

		Purchase purchase = purchaseProduct.getPurchase();

		purchase.changeStatus(status);

		return ResponsePurchase.of(purchaseProduct);

	}

	@Override
	public ResponsePurchase cancelPurchase(Long purchaseId) {
		PurchaseProduct purchaseProduct = purchaseProductRepository.findById(purchaseId)
			.orElseThrow(() -> new IllegalArgumentException("Purchase not found"));

		Purchase purchase = purchaseProduct.getPurchase();

		purchase.changeStatus(PurchaseStatus.CANCELED);

		return ResponsePurchase.of(purchaseProduct);
	}

	@Override
	public List<ResponsePurchase> getPurchaseByUser(String email) {
		return purchaseProductRepository.findAllByUserEmail(email)
			.orElseThrow(() -> new IllegalArgumentException("PurchaseProduct not found"))
			.stream()
			.map(ResponsePurchase::of)
			.toList();
	}

}
