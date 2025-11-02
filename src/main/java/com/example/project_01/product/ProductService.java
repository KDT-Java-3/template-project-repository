package com.example.project_01.product;

import com.example.project_01.entity.ProductEntity;
import com.example.project_01.product.dto.ProductGetRequest;
import com.example.project_01.product.dto.ProductCreateRequest;
import com.example.project_01.product.dto.ProductPatchRequest;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    ProductReporsitory productReporsitory;

    @Autowired
    public ProductService(ProductReporsitory productReporsitory) {
        this.productReporsitory = productReporsitory;
    }

    public ProductGetRequest findById(Integer productId) {
        ProductEntity product = productReporsitory.findById(productId)
                .orElseThrow(()->new IllegalArgumentException("Invalid productId: "+ productId));

        return ProductGetRequest.from(product);
    }

    public ProductGetRequest postProduct(@NonNull ProductCreateRequest productCreateRequest) {
        ProductEntity savedProduct = productReporsitory.save(productCreateRequest.of());
        return ProductGetRequest.from(savedProduct);
    }

    public ProductGetRequest patchProduct(@NonNull ProductPatchRequest request) {
        // TODO PRODUCT 수정하는 기능 추가할 것
        return null;
    }

}
