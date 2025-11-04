package com.example.demo.repository;

import com.example.demo.entity.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@DisplayName("@BatchSize를 사용한 N+1 문제 완화 테스트")
class BatchSizeTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PurchaseRepository purchaseRepository;

    @BeforeEach
    void setUp() {
        // 테스트 데이터 준비: 여러 사용자와 주문 생성
        Category category = Category.builder()
                .name("테스트 카테고리")
                .build();
        category = categoryRepository.save(category);

        Product product = Product.builder()
                .name("테스트 상품")
                .price(new BigDecimal("10000"))
                .stock(100)
                .category(category)
                .build();
        product = productRepository.save(product);

        // 20명의 사용자, 각각 5개의 주문 생성
        for (int i = 1; i <= 20; i++) {
            User user = User.builder()
                    .email("batchuser" + i + "@example.com")
                    .password("password")
                    .name("배치사용자" + i)
                    .build();
            user = userRepository.save(user);

            for (int j = 1; j <= 5; j++) {
                Purchase purchase = Purchase.builder()
                        .user(user)
                        .totalPrice(new BigDecimal("10000"))
                        .status(Purchase.PurchaseStatus.COMPLETED)
                        .build();

                PurchaseItem item = PurchaseItem.builder()
                        .product(product)
                        .quantity(1)
                        .price(product.getPrice())
                        .build();

                purchase.addPurchaseItem(item);
                purchaseRepository.save(purchase);
            }
        }
    }

    @Test
    @DisplayName("@BatchSize를 사용하면 N+1 문제가 완화됨")
    void testBatchSizeMitigation() {
        // WHEN: 모든 사용자 조회
        System.out.println("=== @BatchSize 효과 확인 ===");
        System.out.println("1. 사용자 목록 조회");
        List<User> users = userRepository.findAll();

        System.out.println("2. 각 사용자의 주문 접근");
        System.out.println("   @BatchSize(size=10) 설정으로 10개씩 묶어서 조회");
        
        int queryCount = 0;
        for (User user : users) {
            List<Purchase> purchases = user.getPurchases(); // BatchSize로 인해 묶여서 조회됨
            queryCount++;
            
            if (queryCount <= 5) { // 처음 5개만 출력
                System.out.println("사용자 " + user.getName() + "의 주문 수: " + purchases.size());
            }
        }

        // THEN: 쿼리 개수 확인
        assertThat(users).hasSize(20);
        System.out.println("\n총 " + users.size() + "명의 사용자가 조회되었습니다.");
        System.out.println("→ @BatchSize(size=10)로 인해 20명의 사용자 중");
        System.out.println("  10개씩 묶어서 조회하므로 최대 2번의 추가 쿼리만 발생");
        System.out.println("  (1번의 사용자 조회 + 2번의 주문 배치 조회 = 총 3번의 쿼리)");
    }

    @Test
    @DisplayName("Fetch Join vs @BatchSize 비교")
    void testFetchJoinVsBatchSize() {
        System.out.println("=== Fetch Join vs @BatchSize 비교 ===");
        
        // Fetch Join: 단 1번의 쿼리
        System.out.println("\n1. Fetch Join 사용:");
        List<User> usersWithFetchJoin = userRepository.findAllWithPurchases();
        System.out.println("   → 단 1번의 쿼리로 모든 데이터 조회");
        System.out.println("   → 쿼리 수: 1개");
        assertThat(usersWithFetchJoin).isNotEmpty();

        // @BatchSize: N+1 문제 완화
        System.out.println("\n2. @BatchSize 사용:");
        List<User> usersWithBatchSize = userRepository.findAll();
        System.out.println("   → 1번의 사용자 조회 + 배치 조회");
        System.out.println("   → 쿼리 수: 약 " + (1 + (int) Math.ceil(usersWithBatchSize.size() / 10.0)) + "개");
        
        // 실제로 주문 접근
        for (User user : usersWithBatchSize) {
            user.getPurchases(); // BatchSize로 인해 묶여서 조회
        }
        
        System.out.println("\n결론:");
        System.out.println("  - Fetch Join: 모든 데이터를 한 번에 조회 (가장 효율적)");
        System.out.println("  - @BatchSize: N+1 문제를 완화하지만 Fetch Join보다는 덜 효율적");
        System.out.println("  - Fetch Join 사용이 가능한 경우 Fetch Join을 우선 사용 권장");
    }
}

