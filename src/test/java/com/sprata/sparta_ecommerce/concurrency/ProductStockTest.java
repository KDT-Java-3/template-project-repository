package com.sprata.sparta_ecommerce.concurrency;

import com.sprata.sparta_ecommerce.controller.mapper.ProductMapper;
import com.sprata.sparta_ecommerce.dto.OrderRequestDto;
import com.sprata.sparta_ecommerce.dto.ProductRequestDto;
import com.sprata.sparta_ecommerce.entity.Category;
import com.sprata.sparta_ecommerce.entity.Product;
import com.sprata.sparta_ecommerce.repository.CategoryRepository;
import com.sprata.sparta_ecommerce.repository.OrderRepository;
import com.sprata.sparta_ecommerce.repository.ProductRepository;
import com.sprata.sparta_ecommerce.service.OrderService;
import com.sprata.sparta_ecommerce.service.ProductService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
@ActiveProfiles("test")
public class ProductStockTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductMapper productMapper;

    Category electronics;
    Product product;

    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderRepository orderRepository;

    @BeforeEach
    void setUp() {
        // 카테고리 저장
        electronics = categoryRepository.save(new Category("가전", "전자제품",null));

        ProductRequestDto productRequestDto = new ProductRequestDto(
                "아이폰", "애플폰", 132_000_000L, 10, electronics.getId()
        );

        // 상품 저장
        productService.addProduct(productMapper.toService(productRequestDto));

        this.product = productRepository.findAll().get(0);
    }

    @AfterEach
    void clearUp() {
        orderRepository.deleteAll();
        productRepository.deleteAll();
        categoryRepository.deleteAll();
    }

    @Test
    public void requestOrderAndProductStockTest() throws InterruptedException {
        int threadCount = 5;

        ExecutorService executorService = Executors.newFixedThreadPool(50);
        CountDownLatch countDownLatch = new CountDownLatch(threadCount);

        // 동시요청
        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    orderService.createOrder(new OrderRequestDto(1L, product.getId(), 1, "주소"));
                }finally {
                    countDownLatch.countDown();
                }
            });
        }

        countDownLatch.await();

        // 동시요청 5개
        Product product1 = productRepository.findAll().get(0);
        Assertions.assertThat(product1.getStock()).isEqualTo(5);

    }
}
