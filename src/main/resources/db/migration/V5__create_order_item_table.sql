CREATE TABLE order_item (
                            id BIGINT NOT NULL AUTO_INCREMENT,
                            order_id BIGINT NOT NULL,
                            product_id BIGINT NOT NULL,
                            quantity INT NOT NULL,
                            price DECIMAL(10, 2) NOT NULL COMMENT '주문 시점의 상품 가격',
                            created_at DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6),
                            updated_at DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
                            PRIMARY KEY (id),
                            INDEX idx_order_item_order_id (order_id),
                            INDEX idx_order_item_product_id (product_id)
);

-- 필요하면 FK
-- ALTER TABLE order_item
--   ADD CONSTRAINT fk_order_item_order
--   FOREIGN KEY (order_id) REFERENCES `order`(id);
--
-- ALTER TABLE order_item
--   ADD CONSTRAINT fk_order_item_product
--   FOREIGN KEY (product_id) REFERENCES product(id);
