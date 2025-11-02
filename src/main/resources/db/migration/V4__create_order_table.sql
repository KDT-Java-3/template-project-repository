CREATE TABLE `order` (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         user_id BIGINT NOT NULL COMMENT '주문한 사용자',
                         status VARCHAR(20) NOT NULL DEFAULT 'PENDING' COMMENT 'PENDING, COMPLETED, CANCELED',
                         shipping_address TEXT NOT NULL,
                         total_price DECIMAL(10, 2) NOT NULL DEFAULT 0.00,
                         created_at DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6),
                         updated_at DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6)
);