package com.sparta.bootcamp.java_2_example.domain.product.service;

import com.sparta.bootcamp.java_2_example.domain.product.dto.request.RequestCreateProduct;
import com.sparta.bootcamp.java_2_example.domain.product.dto.request.RequestUpdateProduct;
import com.sparta.bootcamp.java_2_example.domain.product.dto.response.ResponseProduct;

import jakarta.validation.Valid;

/**
 * @author : leeyounggyo
 * @package : com.sparta.bootcamp.java_2_example.domain.product.service
 * @since : 2025. 11. 2.
 */
public interface ProductCommandService {
	ResponseProduct createProduct(RequestCreateProduct requestCreate);

	ResponseProduct updateProduct(Long id, RequestUpdateProduct requestUpdate);
}
