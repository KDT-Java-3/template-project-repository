package com.sparta.bootcamp.java_2_example.domain.product.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sparta.bootcamp.java_2_example.domain.category.entity.Category;
import com.sparta.bootcamp.java_2_example.domain.category.repository.CategoryRepository;
import com.sparta.bootcamp.java_2_example.domain.product.dto.request.RequestCreateProduct;
import com.sparta.bootcamp.java_2_example.domain.product.dto.request.RequestUpdateProduct;
import com.sparta.bootcamp.java_2_example.domain.product.dto.response.ResponseProduct;
import com.sparta.bootcamp.java_2_example.domain.product.dto.search.SearchProduct;
import com.sparta.bootcamp.java_2_example.domain.product.entity.Product;
import com.sparta.bootcamp.java_2_example.domain.product.repository.ProductRepository;
import com.sparta.bootcamp.java_2_example.domain.product.service.ProductCommandService;
import com.sparta.bootcamp.java_2_example.domain.product.service.ProductQueryService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author : leeyounggyo
 * @package : com.sparta.bootcamp.java_2_example.domain.product.service.impl
 * @since : 2025. 11. 2.
 */
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ProductService implements ProductQueryService, ProductCommandService {

	private final ProductRepository productRepository;
	private final CategoryRepository categoryRepository;

	@Override
	public ResponseProduct createProduct(RequestCreateProduct requestCreate) {
		Category category = categoryRepository.findById(requestCreate.getCategoryId())
			.orElseThrow(() -> new IllegalArgumentException("Category not found"));

		Product savedProduct = productRepository.save(Product.of(category, requestCreate));

		return ResponseProduct.of(savedProduct);
	}

	@Override
	public ResponseProduct updateProduct(Long id, RequestUpdateProduct requestUpdate) {
		Product product = productRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("Product not found"));

		Category category = categoryRepository.findById(requestUpdate.getCategoryId())
			.orElseThrow(() -> new IllegalArgumentException("Category not found"));

		product.update(category, requestUpdate);

		return ResponseProduct.of(product);
	}

	// TODO : 검색조건 추가 필요
	@Override
	public List<ResponseProduct> getProducts(SearchProduct search) {
		return productRepository.findAll().stream()
			.map(ResponseProduct::of)
			.toList();
	}

	@Override
	public ResponseProduct getProduct(Long id) {
		Product product = productRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("Product not found"));


		return ResponseProduct.of(product);
	}

}
