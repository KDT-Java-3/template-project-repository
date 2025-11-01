package com.sparta.restful_1week.domain.product.service;

import com.sparta.restful_1week.domain.product.dto.ProductInDTO;
import com.sparta.restful_1week.domain.product.dto.ProductOutDTO;
import com.sparta.restful_1week.domain.product.entity.Product;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductService {
    private final Map<Long, Product> productMap = new HashMap<>();

    public ProductOutDTO createProduct(ProductInDTO productInDTO) {

        // inDto -> entity
        Product product = new Product(productInDTO);

        // category max id chk
        Long maxId = productMap.size() > 0 ? Collections.max(productMap.keySet()) + 1 : 1;
        product.setId(maxId);

        // db 저장
        productMap.put(product.getId(), product);

        // entity -> outDto
        ProductOutDTO productOutDTO = new ProductOutDTO(product);

        return productOutDTO;
    }

    public List<ProductOutDTO> getProduct() {
        // Map to list
        List<ProductOutDTO> productOutDTOList = productMap.values().stream()
                .map(ProductOutDTO::new).toList();

        return productOutDTOList;
    }

    public ProductOutDTO updateProduct(Long id, ProductInDTO productInDTO) {
        // 해당 메모가 DB에 존재하는지 확인
        if(productMap.containsKey(id)) {
            // 해당 ID로 카테고리 가져오기
            Product product = productMap.get(id);

            // 메모 수정
            product.updateProduct(productInDTO);

            // entity -> outDto
            ProductOutDTO productOutDTO = new ProductOutDTO(product);

            return productOutDTO;

        } else {
            throw new IllegalArgumentException("선택한 상품 데이터는 존재하지 않습니다.");
        }
    }
}
