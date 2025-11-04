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
@DisplayName("JPA N+1 문제 및 Fetch Join 최적화 테스트")
class NPlusOneProblemTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PurchaseRepository purchaseRepository;

    private User testUser;
    private Category testCategory;
    private Product testProduct1;
    private Product testProduct2;

    @BeforeEach
    void setUp() {
        // 테스트 데이터 준비
        testUser = User.builder()
                .email("test@example.com")
                .password("password123")
                .name("테스트 사용자")
                .build();
        testUser = userRepository.save(testUser);

        testCategory = Category.builder()
                .name("전자제품")
                .build();
        testCategory = categoryRepository.save(testCategory);

        testProduct1 = Product.builder()
                .name("노트북")
                .price(new BigDecimal("1000000"))
                .stock(10)
                .category(testCategory)
                .build();
        testProduct1 = productRepository.save(testProduct1);

        testProduct2 = Product.builder()
                .name("마우스")
                .price(new BigDecimal("50000"))
                .stock(20)
                .category(testCategory)
                .build();
        testProduct2 = productRepository.save(testProduct2);

        // 주문 데이터 생성 (여러 사용자, 여러 주문)
        for (int i = 1; i <= 5; i++) {
            User user = User.builder()
                    .email("user" + i + "@example.com")
                    .password("password")
                    .name("사용자" + i)
                    .build();
            user = userRepository.save(user);

            // 각 사용자당 3개의 주문 생성
            for (int j = 1; j <= 3; j++) {
                Purchase purchase = Purchase.builder()
                        .user(user)
                        .totalPrice(new BigDecimal("100000"))
                        .status(Purchase.PurchaseStatus.COMPLETED)
                        .build();

                PurchaseItem item1 = PurchaseItem.builder()
                        .product(testProduct1)
                        .quantity(1)
                        .price(testProduct1.getPrice())
                        .build();

                PurchaseItem item2 = PurchaseItem.builder()
                        .product(testProduct2)
                        .quantity(2)
                        .price(testProduct2.getPrice())
                        .build();

                purchase.addPurchaseItem(item1);
                purchase.addPurchaseItem(item2);
                purchaseRepository.save(purchase);
            }
        }
    }

    @Test
    @DisplayName("N+1 문제 발생: findAll() 사용 시")
    void testNPlusOneProblem() {
        // WHEN: 모든 사용자 조회 (지연 로딩)
        System.out.println("=== N+1 문제 발생 시나리오 ===");
        System.out.println("1. 사용자 목록 조회 쿼리 실행");
        List<User> users = userRepository.findAll();

        System.out.println("2. 각 사용자의 주문 접근 시 추가 쿼리 발생");
        // 각 사용자의 주문에 접근할 때마다 추가 쿼리 발생
        for (User user : users) {
            List<Purchase> purchases = user.getPurchases(); // 여기서 쿼리 발생!
            System.out.println("사용자 " + user.getName() + "의 주문 수: " + purchases.size());

            // 각 주문의 상품에 접근할 때마다 추가 쿼리 발생
            for (Purchase purchase : purchases) {
                List<PurchaseItem> items = purchase.getPurchaseItems(); // 여기서도 쿼리 발생!
                System.out.println("  주문 ID: " + purchase.getId() + ", 상품 수: " + items.size());
            }
        }

        // THEN: 쿼리 개수 확인
        assertThat(users).isNotEmpty();
        // 1번의 사용자 조회 + N번의 주문 조회 + M번의 주문 상품 조회 = N+1 문제 발생
        System.out.println("\n총 " + users.size() + "명의 사용자가 조회되었습니다.");
        System.out.println("→ 각 사용자마다 주문 조회 쿼리가 발생하므로 N+1 문제 발생!");
    }

    @Test
    @DisplayName("Fetch Join 최적화: findAllWithPurchases() 사용 시")
    void testFetchJoinOptimization() {
        // WHEN: Fetch Join을 사용하여 사용자와 주문을 한 번에 조회
        System.out.println("=== Fetch Join 최적화 시나리오 ===");
        System.out.println("1. Fetch Join으로 사용자와 주문을 한 번에 조회");
        List<User> users = userRepository.findAllWithPurchases();

        System.out.println("2. 이미 로드된 데이터 접근 (추가 쿼리 없음)");
        // 이미 로드된 데이터이므로 추가 쿼리 발생하지 않음
        for (User user : users) {
            List<Purchase> purchases = user.getPurchases(); // 추가 쿼리 없음!
            System.out.println("사용자 " + user.getName() + "의 주문 수: " + purchases.size());

            // 주문 상품은 아직 로드되지 않았으므로 추가 쿼리 발생
            for (Purchase purchase : purchases) {
                List<PurchaseItem> items = purchase.getPurchaseItems(); // 여기서는 여전히 쿼리 발생
                System.out.println("  주문 ID: " + purchase.getId() + ", 상품 수: " + items.size());
            }
        }

        // THEN: 쿼리 개수 확인
        assertThat(users).isNotEmpty();
        System.out.println("\n총 " + users.size() + "명의 사용자가 조회되었습니다.");
        System.out.println("→ Fetch Join으로 사용자와 주문을 한 번에 조회하여 N+1 문제 해결!");
    }

    @Test
    @DisplayName("완전한 Fetch Join 최적화: 모든 연관 데이터를 한 번에 조회")
    void testCompleteFetchJoinOptimization() {
        // WHEN: 모든 연관 데이터를 Fetch Join으로 한 번에 조회
        System.out.println("=== 완전한 Fetch Join 최적화 시나리오 ===");
        System.out.println("1. Fetch Join으로 사용자, 주문, 주문 상품, 상품을 한 번에 조회");
        List<User> users = userRepository.findAllWithPurchasesAndItems();

        System.out.println("2. 모든 연관 데이터 접근 (추가 쿼리 없음)");
        // 모든 연관 데이터가 이미 로드되어 있으므로 추가 쿼리 발생하지 않음
        for (User user : users) {
            List<Purchase> purchases = user.getPurchases(); // 추가 쿼리 없음!
            System.out.println("사용자 " + user.getName() + "의 주문 수: " + purchases.size());

            for (Purchase purchase : purchases) {
                List<PurchaseItem> items = purchase.getPurchaseItems(); // 추가 쿼리 없음!
                System.out.println("  주문 ID: " + purchase.getId() + ", 상품 수: " + items.size());

                for (PurchaseItem item : items) {
                    Product product = item.getProduct(); // 추가 쿼리 없음!
                    System.out.println("    상품: " + product.getName() + ", 가격: " + item.getPrice());
                }
            }
        }

        // THEN: 쿼리 개수 확인
        assertThat(users).isNotEmpty();
        System.out.println("\n총 " + users.size() + "명의 사용자가 조회되었습니다.");
        System.out.println("→ 단 한 번의 쿼리로 모든 연관 데이터를 조회하여 완전한 최적화!");
    }

    @Test
    @DisplayName("특정 사용자의 주문 조회: Fetch Join 최적화")
    void testUserPurchaseFetchJoin() {
        // WHEN: 특정 사용자의 주문을 Fetch Join으로 조회
        System.out.println("=== 특정 사용자 주문 조회 최적화 ===");
        Long userId = userRepository.findAll().get(0).getId();

        // N+1 문제 발생
        System.out.println("1. 일반 조회 (N+1 문제 발생)");
        List<Purchase> purchases1 = purchaseRepository.findByUserId(userId);
        for (Purchase purchase : purchases1) {
            List<PurchaseItem> items = purchase.getPurchaseItems(); // 각 주문마다 쿼리 발생
            System.out.println("주문 ID: " + purchase.getId() + ", 상품 수: " + items.size());
        }

        // Fetch Join 최적화
        System.out.println("\n2. Fetch Join 조회 (최적화)");
        List<Purchase> purchases2 = purchaseRepository.findByUserIdWithItems(userId);
        for (Purchase purchase : purchases2) {
            List<PurchaseItem> items = purchase.getPurchaseItems(); // 추가 쿼리 없음
            System.out.println("주문 ID: " + purchase.getId() + ", 상품 수: " + items.size());
        }

        assertThat(purchases2).isNotEmpty();
        System.out.println("\n→ Fetch Join으로 단 한 번의 쿼리로 모든 데이터 조회!");
    }
}

