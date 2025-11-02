package com.sparta.bootcamp.java_2_example.domain.product.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sparta.bootcamp.java_2_example.domain.category.dto.search.SearchCategory;
import com.sparta.bootcamp.java_2_example.domain.product.dto.request.RequestCreateProduct;
import com.sparta.bootcamp.java_2_example.domain.product.dto.request.RequestUpdateProduct;
import com.sparta.bootcamp.java_2_example.domain.product.dto.response.ResponseProduct;
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

	@Override
	public ResponseProduct createProduct(RequestCreateProduct requestCreate) {
		return null;
	}

	@Override
	public ResponseProduct updateProduct(Long id, RequestUpdateProduct requestUpdate) {
		return null;
	}

	@Override
	public List<ResponseProduct> getProducts(SearchCategory search) {
		return List.of();
	}

	@Override
	public ResponseProduct getProduct(Long id) {
		return null;
	}

}
