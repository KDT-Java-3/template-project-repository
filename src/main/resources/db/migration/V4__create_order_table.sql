CREATE TABLE `order` (
                         id BIGINT NOT NULL AUTO_INCREMENT,
                         user_id BIGINT NOT NULL COMMENT '주문한 사용자',
                         status VARCHAR(20) NOT NULL DEFAULT 'PENDING' COMMENT 'PENDING, COMPLETED, CANCELED',
                         shipping_address TEXT NOT NULL,
                         total_price DECIMAL(10, 2) NOT NULL DEFAULT 0.00,
                         created_at DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6),
                         updated_at DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
                         PRIMARY KEY (id),
                         INDEX idx_order_user_id (user_id),
                         INDEX idx_order_status (status)
);

-- 필요하면
-- ALTER TABLE `order`
--   ADD CONSTRAINT fk_order_user
--   FOREIGN KEY (user_id) REFERENCES `user`(id);
