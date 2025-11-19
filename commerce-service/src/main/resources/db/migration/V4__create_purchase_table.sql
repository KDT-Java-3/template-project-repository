CREATE TABLE purchase
(
    id               BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id          BIGINT         NOT NULL COMMENT '구매한 사용자 ID',
    total_price      DECIMAL(10, 2) NOT NULL,
    status           VARCHAR(20) DEFAULT 'PENDING' COMMENT 'PENDING, COMPLETED, CANCELED, REFUNDED',
    shipping_address TEXT           NOT NULL,
    created_at       DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6),
    updated_at       DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6)
);

CREATE TABLE purchase_product
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    purchase_id BIGINT         NOT NULL COMMENT '어떤 주문에 속하는지',
    product_id  BIGINT         NOT NULL COMMENT '어떤 상품인지',
    quantity    INT            NOT NULL,
    price       DECIMAL(10, 2) NOT NULL COMMENT '주문 시점의 상품 가격',
    created_at  DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6),
    updated_at  DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6)
);