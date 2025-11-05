CREATE TABLE orders
(
    id               BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id          BIGINT,
    status           VARCHAR(50),
    shipping_address VARCHAR(255),
    total_price      DECIMAL(12, 2),
    created_at       DATETIME DEFAULT CURRENT_TIMESTAMP
);
