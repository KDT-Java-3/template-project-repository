package com.sparta.bootcamp.java_2_example.domain.product.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.bootcamp.java_2_example.domain.product.dto.request.RequestCreateProduct;
import com.sparta.bootcamp.java_2_example.domain.product.dto.request.RequestUpdateProduct;
import com.sparta.bootcamp.java_2_example.domain.product.dto.response.ResponseProduct;
import com.sparta.bootcamp.java_2_example.domain.product.dto.search.SearchProduct;
import com.sparta.bootcamp.java_2_example.domain.product.service.ProductCommandService;
import com.sparta.bootcamp.java_2_example.domain.product.service.ProductQueryService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author : leeyounggyo
 * @package : com.sparta.bootcamp.java_2_example.domain.product.controller
 * @since : 2025. 11. 2.
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/products")
public class ProductController {

	private final ProductQueryService productQueryService;
	private final ProductCommandService productCommandService;

	@PostMapping
	public ResponseEntity<ResponseProduct> createProduct(

		@Valid
		@RequestBody
		RequestCreateProduct requestCreate

	) {

		return ResponseEntity.ok(productCommandService.createProduct(requestCreate));
	}

	@PatchMapping("/{id}")
	public ResponseEntity<ResponseProduct> updateProduct(

		@PathVariable
		Long id,

		@Valid
		@RequestBody
		RequestUpdateProduct requestUpdate

	) {

		return ResponseEntity.ok(productCommandService.updateProduct(id, requestUpdate));
	}

	@GetMapping
	public ResponseEntity<List<ResponseProduct>> getProducts(

		@ModelAttribute
		SearchProduct search

	) {

		return ResponseEntity.ok(productQueryService.getProducts(search));
	}

	@GetMapping("/{id}")
	public ResponseEntity<ResponseProduct> getProduct(

		@PathVariable
		Long id

	) {

		return ResponseEntity.ok(productQueryService.getProduct(id));
	}

}
