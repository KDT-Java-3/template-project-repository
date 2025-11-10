package com.example.demo.domain.product.service;

import com.example.demo.domain.order.entity.OrderStatus;
import com.example.demo.domain.order.repository.OrderRepository;
import com.example.demo.domain.product.dto.request.ProductCreateRequest;
import com.example.demo.domain.product.dto.request.ProductSearchCondition;
import com.example.demo.domain.product.dto.request.ProductUpdateRequest;
import com.example.demo.domain.product.dto.response.ProductResponse;
import com.example.demo.domain.product.dto.response.ProductSummary;
import com.example.demo.domain.product.entity.Product;
import com.example.demo.domain.product.entity.QProduct;
import com.example.demo.domain.product.mapper.ProductMapper;
import com.example.demo.domain.product.repository.ProductQueryRepository;
import com.example.demo.domain.product.repository.ProductRepository;
import com.example.demo.global.exception.ServiceException;
import com.example.demo.global.exception.ServiceExceptionCode;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductQueryRepository productQueryRepository;
    private final OrderRepository orderRepository;
    private final ProductMapper productMapper;
    private final EntityManager entityManager;
    private final JPAQueryFactory queryFactory;

    /**
     * 상품 등록 (QueryDSL로 ID 반환)
     */
    @Transactional
    public Long createProduct(ProductCreateRequest request) {
        Product product = productMapper.toEntity(request);
        entityManager.persist(product);

        // 등록 직후 QueryDSL로 ID 셀렉트 (요구사항 충족용)
        QProduct q = QProduct.product;
        Long id = queryFactory.select(q.id)
            .from(q)
            .where(q.name.eq(request.getName()))
            .orderBy(q.id.desc())
            .limit(1)
            .fetchOne();

        return id != null ? id : product.getId();
    }

    /**
     * 단일 상품 조회
     */
    public ProductResponse getProductById(Long id) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new ServiceException(ServiceExceptionCode.NOT_FOUND_PRODUCT));
        return productMapper.toResponse(product);
    }

    /**
     * 상품 검색 (조건부 + 페이징 + 정렬)
     */
    public Page<ProductSummary> searchProducts(ProductSearchCondition condition, Pageable pageable) {
        return productQueryRepository.search(condition, pageable);
    }

    /**
     * 상품 수정
     */
    @Transactional
    public ProductResponse updateProduct(Long id, ProductUpdateRequest request) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new ServiceException(ServiceExceptionCode.NOT_FOUND_PRODUCT));

        product.update(
            request.getName(),
            request.getDescription(),
            request.getPrice(),
            request.getStock(),
            request.getCategoryId()
        );

        return productMapper.toResponse(product);
    }

    /**
     * 상품 삭제 (주문 완료 상태 검증)
     */
    @Transactional
    public void deleteProduct(Long id) {
        // 상품 존재 여부 확인
        if (!productRepository.existsById(id)) {
            throw new ServiceException(ServiceExceptionCode.NOT_FOUND_PRODUCT);
        }

        // 완료된 주문 존재 여부 확인
        boolean hasCompletedOrders = orderRepository.existsByProductIdAndStatus(id, OrderStatus.COMPLETED);
        if (hasCompletedOrders) {
            throw new ServiceException(ServiceExceptionCode.PRODUCT_HAS_COMPLETED_ORDERS);
        }

        productRepository.deleteById(id);
    }
}
