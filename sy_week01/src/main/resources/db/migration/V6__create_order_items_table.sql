CREATE TABLE order_items (
     id              BIGINT PRIMARY KEY AUTO_INCREMENT,
     order_id        BIGINT NOT NULL,
     product_id      BIGINT NOT NULL,
     product_name    VARCHAR(200) NOT NULL,   -- 주문 시점 스냅샷
     product_price   DECIMAL(18,2) NOT NULL,  -- 주문 시점 스냅샷
     quantity        INT NOT NULL,
     subtotal_amount DECIMAL(18,2) NOT NULL
);