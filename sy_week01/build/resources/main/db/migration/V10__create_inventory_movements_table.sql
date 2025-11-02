CREATE TABLE inventory_movements (
     id                BIGINT PRIMARY KEY AUTO_INCREMENT,
     product_id        BIGINT NOT NULL,
     change_qty        INT NOT NULL,        -- 감소는 음수, 복원은 양수
     reason            ENUM('order_decrease','refund_restore','manual_adjust') NOT NULL,
     ref_order_item_id BIGINT NULL,
     ref_refund_item_id BIGINT NULL,
     created_at        TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);