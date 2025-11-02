package bootcamp.project.domain.purchase.service;

import bootcamp.project.common.enums.PurchaseStatus;
import bootcamp.project.domain.product.entity.Product;
import bootcamp.project.domain.product.repository.ProductRepository;
import bootcamp.project.domain.purchase.dto.CreatePurchaseDto;
import bootcamp.project.domain.purchase.dto.CreatePurchaseProductDto;
import bootcamp.project.domain.purchase.entity.Purchase;
import bootcamp.project.domain.purchase.entity.PurchaseProduct;
import bootcamp.project.domain.purchase.repository.PurchaseProductRepository;
import bootcamp.project.domain.purchase.repository.PurchaseRepository;
import bootcamp.project.domain.user.entity.User;
import bootcamp.project.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PurchaseService {
    private final PurchaseRepository purchaseRepository;
    private final PurchaseProductRepository purchaseProductRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public void addCart(CreatePurchaseDto createPurchaseDto) {
        User user = userRepository.findById(createPurchaseDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        Product product = productRepository.findById(createPurchaseDto.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));

        product.decreaseStock(createPurchaseDto.getQuantity());

        // 구매목록 찾기 및 생성
        Purchase purchase = getPurchase( createPurchaseDto, user);

        // 구매물품 생성
        creatPurchaseProduct( product, purchase, createPurchaseDto);

        // 총 금액 계산
        List<PurchaseProduct> purchaseProducts = purchaseProductRepository.findByPurchase_Id(purchase.getId());
        BigDecimal totalPrice = purchaseProducts.stream()
                .map(pp -> pp.getPrice().multiply(BigDecimal.valueOf(pp.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // 총 금액 업데이트
        purchase.updateTotalPrice(totalPrice);
    }

    public Purchase getPurchase(CreatePurchaseDto createPurchaseDto, User user) {
        Purchase purchase;
        if (createPurchaseDto.getPurchaseId() != null) {
            purchase = purchaseRepository.findById(createPurchaseDto.getPurchaseId())
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 주문입니다."));
        } else {
            purchase = Purchase.builder()
                    .user(user)
                    .totalPrice(BigDecimal.ZERO)
                    .status(PurchaseStatus.PENDING)
                    .shippingAddress(createPurchaseDto.getShippingAddress())
                    .build();
            purchase = purchaseRepository.save(purchase);
        }
        return purchase;
    }

    public void creatPurchaseProduct( Product product, Purchase purchase, CreatePurchaseDto createPurchaseDto) {
        CreatePurchaseProductDto createPurchaseProductDto = CreatePurchaseProductDto.builder()
                .purchase(purchase)
                .product(product)
                .quantity(createPurchaseDto.getQuantity())
                .price(product.getPrice())
                .build();

        PurchaseProduct purchaseProduct = PurchaseProduct.builder()
                .purchase(createPurchaseProductDto.getPurchase())
                .product(createPurchaseProductDto.getProduct())
                .quantity(createPurchaseProductDto.getQuantity())
                .price(createPurchaseProductDto.getPrice())
                .build();

        purchaseProductRepository.save(purchaseProduct);
    }

    public List<PurchaseProduct> searchPurchaseProduct(Long purchaseId) {
        return  purchaseProductRepository.findByPurchase_Id(purchaseId);
    }
}
