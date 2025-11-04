CREATE TABLE purchase_product
(
    id          BIGINT       NOT NULL AUTO_INCREMENT,
    purchase_id BIGINT       NOT NULL,
    product_id BIGINT       NOT NULL,
    quantity   INT          NOT NULL,
    price      DECIMAL(10, 2) NOT NULL,
    created_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    FOREIGN KEY (purchase_id) REFERENCES purchase(id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES product(id),
    INDEX idx_purchase_id (purchase_id),
    INDEX idx_product_id (product_id),
    UNIQUE KEY uk_purchase_product (purchase_id, product_id)
);

