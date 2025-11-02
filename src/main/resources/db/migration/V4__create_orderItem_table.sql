CREATE TABLE order_item
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id    BIGINT,
    product_id  BIGINT,
    quantity    INT,
    unit_price  DECIMAL(10, 2),
    total_price DECIMAL(12, 2)
);
