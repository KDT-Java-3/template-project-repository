-- 연결 테이블: `purchase_product`
CREATE TABLE purchase_product (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    purchase_id BIGINT NOT NULL,   -- FK: 어떤 주문에 속하는지
    product_id BIGINT NOT NULL,    -- FK: 어떤 상품인지
    quantity INT NOT NULL,         -- 주문 수량
    price DECIMAL(10, 2) NOT NULL,  -- 주문 시점의 상품 가격
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);