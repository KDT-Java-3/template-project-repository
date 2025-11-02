package com.pepponechoi.project.domain.product.service;

import com.pepponechoi.project.domain.product.dto.request.ProductCreateRequest;
import com.pepponechoi.project.domain.product.dto.request.ProductReadRequest;
import com.pepponechoi.project.domain.product.dto.request.ProductUpdateRequest;
import com.pepponechoi.project.domain.product.dto.response.ProductResponse;
import java.util.List;

public interface ProductService {
    ProductResponse create(ProductCreateRequest request);
    List<ProductResponse> list(ProductReadRequest request);
    ProductResponse get(Long id);
    Boolean update(Long id, ProductUpdateRequest request);
    Boolean delete(Long id, Long userId);
}
