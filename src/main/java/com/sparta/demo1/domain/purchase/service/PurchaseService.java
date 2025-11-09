package com.sparta.demo1.domain.purchase.service;

import com.sparta.demo1.common.error.CustomException;
import com.sparta.demo1.common.error.ExceptionCode;
import com.sparta.demo1.domain.product.dto.response.ProductResDto;
import com.sparta.demo1.domain.product.entity.ProductEntity;
import com.sparta.demo1.domain.product.repository.ProductRepository;
import com.sparta.demo1.domain.purchase.dto.mapper.PurchaseMapper;
import com.sparta.demo1.domain.purchase.dto.request.PurchaseReqDto;
import com.sparta.demo1.domain.purchase.dto.response.PurchaseResDto;
import com.sparta.demo1.domain.purchase.entity.PurchaseEntity;
import com.sparta.demo1.domain.purchase.entity.PurchaseProductEntity;
import com.sparta.demo1.domain.purchase.enums.PurchaseOrderBy;
import com.sparta.demo1.domain.purchase.enums.PurchaseStatus;
import com.sparta.demo1.domain.purchase.repository.PurchaseProductRepository;
import com.sparta.demo1.domain.purchase.repository.PurchaseQueryDsl;
import com.sparta.demo1.domain.purchase.repository.PurchaseRepository;
import com.sparta.demo1.domain.user.entity.UserEntity;
import com.sparta.demo1.domain.user.repository.UserRepository;
import jakarta.persistence.LockModeType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final PurchaseProductRepository purchaseProductRepository;

    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    private final PurchaseQueryDsl  purchaseQueryDsl;

    private final PurchaseMapper purchaseMapper;

    @Transactional(readOnly = true)
    public List<PurchaseProductEntity> findPurchasesOfCompletedStateByProductId(Long productId) {
        return purchaseQueryDsl.findPurchasesOfCompletedStateByProductId(productId);
    }

    @Transactional(readOnly = true)
    public PurchaseResDto.PurchaseDetailInfo getPurchaseDetailInfo(Long id){
        PurchaseResDto.PurchaseDetailInfo purchaseDetailInfo = new PurchaseResDto.PurchaseDetailInfo();

        PurchaseEntity purchaseEntity = purchaseQueryDsl.findAllProductInfoById(id)
                .orElseThrow(() -> new CustomException(ExceptionCode.NOT_EXIST));

        purchaseDetailInfo.setPurchaseInfo(PurchaseResDto.PurchaseInfo.builder()
                        .id(purchaseEntity.getId())
                        .status(purchaseEntity.getStatus())
                        .shippingAddress(purchaseEntity.getShippingAddress())
                        .status(purchaseEntity.getStatus())
                .build());

        List<ProductResDto.ProductInfo> productInfoList = new ArrayList<>();

        purchaseEntity.getPurchaseProductList().forEach(purchaseProductEntity -> {
            ProductEntity productEntity = purchaseProductEntity.getProduct();

            productInfoList.add(ProductResDto.ProductInfo.builder()
                            .id(productEntity.getId())
                            .name(productEntity.getName())
                            .description(productEntity.getDescription())
                            .stock(productEntity.getStock())
                    .build());
        });

        purchaseDetailInfo.setProductInfoList(productInfoList);

        return purchaseDetailInfo;
    }

    @Transactional(readOnly = true)
    public Page<PurchaseResDto.PurchaseInfo> findPurchaseFilter(
            Long userId,
            PurchaseStatus purchaseStatus,
            LocalDateTime startDate,
            LocalDateTime endDate,
            Pageable pageable,
            List<PurchaseOrderBy> orderByList
    ){
        Page<PurchaseEntity> purchaseEntityPage = purchaseQueryDsl.findPurchaseOfFilter(
           userId,
           purchaseStatus,
           startDate,
           endDate,
           pageable,
           orderByList
        );

        // Entity → DTO 매핑
        List<PurchaseResDto.PurchaseInfo> dtoList = purchaseEntityPage.getContent()
                .stream()
                .map(purchaseMapper::toRes)
                .toList();

        return new PageImpl<>(dtoList, pageable, purchaseEntityPage.getTotalElements());
    }

    @Transactional
    public Long createPurchase(Long userId, String shippingAddress, List<PurchaseReqDto.PurchaseCreateProductInfo> purchaseCreateProductInfoList){
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ExceptionCode.NOT_EXIST));

        List<Long> productIdList = purchaseCreateProductInfoList.stream()
                .map(PurchaseReqDto.PurchaseCreateProductInfo::getProductId) // productId getter
                .toList();

        // 비관적락이 걸린 쿼리로 상품 데이터 가져오기
        List<ProductEntity> productEntityList = productRepository.findAllByIdWithLock(productIdList);

        if (productEntityList.size() != productIdList.size()) {
            throw new CustomException(ExceptionCode.NOT_EXIST);
        }

        // 상품별 수량 매핑
        Map<Long, Integer> productQuantityMap = purchaseCreateProductInfoList.stream()
                .collect(Collectors.toMap(PurchaseReqDto.PurchaseCreateProductInfo::getProductId,
                        PurchaseReqDto.PurchaseCreateProductInfo::getQuantity));

        BigDecimal totalPrice = BigDecimal.ZERO;

        // 재고 체크 및 총 금액 계산
        for (ProductEntity productEntity : productEntityList) {
            Integer quantity = productQuantityMap.get(productEntity.getId());
            if (productEntity.getStock() < quantity) {
                throw new CustomException(ExceptionCode.INTERNAL_SERVER_ERROR, productEntity.getName() + " 수량만큼 재고가 없습니다.");
            }
            productEntity.updateStock(productEntity.getStock() - quantity);
            totalPrice = totalPrice.add(productEntity.getPrice().multiply(BigDecimal.valueOf(quantity)));
        }

        // 구매 엔티티 저장
        PurchaseEntity purchaseEntity = purchaseRepository.save(PurchaseEntity.builder()
                .user(userEntity)
                .status(PurchaseStatus.PENDING)
                .shippingAddress(shippingAddress)
                .totalPrice(totalPrice)
                .build());

        // 구매-상품 관계 저장
        List<PurchaseProductEntity> purchaseProductEntities = productEntityList.stream()
                .map(productEntity -> {
                    Integer quantity = productQuantityMap.get(productEntity.getId());
                    return PurchaseProductEntity.builder()
                            .purchase(purchaseEntity)
                            .product(productEntity)
                            .quantity(quantity)
                            .price(productEntity.getPrice())
                            .build();
                })
                .toList();
        purchaseProductRepository.saveAll(purchaseProductEntities);

        return purchaseEntity.getId();
    }

    @Transactional
    public void deletePurchase(Long id) {
        PurchaseEntity purchaseEntity = purchaseQueryDsl.findAllProductInfoById(id)
                .orElseThrow(() -> new CustomException(ExceptionCode.NOT_EXIST));

        if(purchaseEntity.getStatus() != PurchaseStatus.PENDING){
            throw new CustomException(ExceptionCode.INTERNAL_SERVER_ERROR, "주문을 취소 할 수 없는 상태입니다.");
        }

        purchaseEntity.updateStatus(PurchaseStatus.CANCELED);

        // 상품 재고 다시 복원
        List<ProductEntity> productList = purchaseEntity.getPurchaseProductList().stream()
                .map(p -> {
                    ProductEntity product = p.getProduct();
                    product.updateStock(product.getStock() + p.getQuantity());
                    return product;
                })
                .toList();

        productRepository.saveAll(productList); // batch update
    }
}
