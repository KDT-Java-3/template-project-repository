package com.sparta.templateprojectrepository.service;

import com.sparta.templateprojectrepository.PurchaseStatus;
import com.sparta.templateprojectrepository.dto.request.PurchaseCreateRequestDto;
import com.sparta.templateprojectrepository.entity.Product;
import com.sparta.templateprojectrepository.entity.Purchase;
import com.sparta.templateprojectrepository.entity.PurchaseProduct;
import com.sparta.templateprojectrepository.entity.User;
import com.sparta.templateprojectrepository.repository.ProductRepository;
import com.sparta.templateprojectrepository.repository.PurchaseRepository;
import com.sparta.templateprojectrepository.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class PurchaseService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private PurchaseRepository purchaseRepository;

    public void createPurchase(PurchaseCreateRequestDto dto) {
        User user = userRepository.findById(dto.getUser_id()).orElseThrow(()->new IllegalArgumentException("사용자 정보 없음."));
        BigDecimal totalPrice = BigDecimal.ZERO;

        for(PurchaseProduct purchasedProduct : dto.getPurchasedProductList()){
            Product product = productRepository.findById(purchasedProduct.getId())
                    .orElseThrow(()-> new IllegalArgumentException("존재하지 않는 상품"));

            //재고수량 감소, 저장
            product.decreseStock(purchasedProduct.getQuantity());
            productRepository.save(product);

            BigDecimal productTotalPrice = purchasedProduct.getPrice().multiply(new BigDecimal(purchasedProduct.getQuantity()));
            totalPrice = totalPrice.add(productTotalPrice);
        }

        Purchase purchase = Purchase.builder().user(user)
                .shipping_address(user.getAddress())
                .totalPrice(totalPrice)
                .status(PurchaseStatus.pending).build();

        purchaseRepository.save(purchase);
    }


    public Optional<Purchase> getPurchase(Long id) {
        return purchaseRepository.findById(id);
    }

    public void updateStatus(Purchase purchase) {
        Purchase pastpurchase = purchaseRepository.findById(purchase.getId()).orElseThrow(()-> new IllegalArgumentException("주문이 조회되지 않음"));

        Purchase updatedPurchase = Purchase.builder()
                .status(pastpurchase.getStatus() == PurchaseStatus.pending ? purchase.getStatus() : PurchaseStatus.pending)
                .id(pastpurchase.getId())
                .build();

        purchaseRepository.save(updatedPurchase);

    }
}
