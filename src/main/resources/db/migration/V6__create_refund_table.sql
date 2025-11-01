CREATE TABLE refund (
                        id BIGINT NOT NULL AUTO_INCREMENT,
                        user_id BIGINT NOT NULL,
                        order_id BIGINT NOT NULL,
                        reason TEXT NOT NULL,
                        status VARCHAR(20) NOT NULL DEFAULT 'PENDING' COMMENT 'PENDING, APPROVED, REJECTED',
                        created_at DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6),
                        updated_at DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
                        PRIMARY KEY (id),
                        INDEX idx_refund_user_id (user_id),
                        INDEX idx_refund_order_id (order_id),
                        INDEX idx_refund_status (status)
);

-- 필요하면 FK
-- ALTER TABLE refund
--   ADD CONSTRAINT fk_refund_user
--   FOREIGN KEY (user_id) REFERENCES `user`(id);
--
-- ALTER TABLE refund
--   ADD CONSTRAINT fk_refund_order
--   FOREIGN KEY (order_id) REFERENCES `order`(id);
