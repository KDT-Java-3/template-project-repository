package io.depark.commerceservice.service;

import io.depark.commerceservice.common.ServiceException;
import io.depark.commerceservice.common.ServiceExceptionCode;
import io.depark.commerceservice.controller.dto.ProductRequest;
import io.depark.commerceservice.controller.dto.ProductResponse;
import io.depark.commerceservice.controller.dto.ProductSearchCondition;
import io.depark.commerceservice.entity.Category;
import io.depark.commerceservice.entity.Product;
import io.depark.commerceservice.entity.enums.PurchaseStatus;
import io.depark.commerceservice.repository.CategoryJpaRepository;
import io.depark.commerceservice.repository.ProductJpaRepository;
import io.depark.commerceservice.repository.ProductQueryRepository;
import io.depark.commerceservice.repository.PurchaseProductJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductJpaRepository productJpaRepository;
    private final CategoryJpaRepository categoryJpaRepository;
    private final PurchaseProductJpaRepository purchaseProductJpaRepository;
    private final ProductQueryRepository productQueryRepository;

    @Transactional(readOnly = true)
    public ProductResponse getProduct(Long id) {
        Product product = productJpaRepository.findById(id)
                .orElseThrow(() -> new ServiceException(ServiceExceptionCode.NOT_FOUND_PRODUCT));
        return ProductResponse.fromEntity(product);
    }

    @Transactional(readOnly = true)
    public Page<ProductResponse> searchProducts(ProductSearchCondition condition, Pageable pageable) {
        Page<Product> products = productQueryRepository.searchProducts(condition, pageable);
        return products.map(ProductResponse::fromEntity);
    }

    @Transactional
    public ProductResponse create(ProductRequest request) {
        Category category = categoryJpaRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ServiceException(ServiceExceptionCode.NOT_FOUND_CATEGORY));

        return ProductResponse.fromEntity(
                productJpaRepository.save(
                        Product.builder()
                                .category(category)
                                .name(request.getName())
                                .description(request.getDescription())
                                .price(request.getPrice())
                                .stock(request.getStock())
                                .build()
                )
        );
    }

    @Transactional
    public ProductResponse update(Long id, ProductRequest request) {
        Product product = productJpaRepository.findById(id)
                .orElseThrow(() -> new ServiceException(ServiceExceptionCode.NOT_FOUND_PRODUCT));

        Category category = categoryJpaRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ServiceException(ServiceExceptionCode.NOT_FOUND_CATEGORY));

        product.updateDetails(category, request.getName(), request.getDescription(), request.getPrice(), request.getStock());

        return ProductResponse.fromEntity(product);
    }

    @Transactional
    public void delete(Long id) {
        // 연관된 주문 상태 검늗 - 주문 완료 상태면 삭제 불가
        boolean isProductActive = purchaseProductJpaRepository.existsByProduct_IdAndPurchase_Status(id, PurchaseStatus.COMPLETED);
        if (isProductActive) {
            throw new ServiceException(ServiceExceptionCode.NOT_ALLOWED_DELETE_PRODUCT);
        }

        Product product = productJpaRepository.findById(id)
                .orElseThrow(() -> new ServiceException(ServiceExceptionCode.NOT_FOUND_PRODUCT));

        productJpaRepository.delete(product);
    }
}
