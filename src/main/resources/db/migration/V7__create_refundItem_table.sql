CREATE TABLE refund_item
(
    id            BIGINT AUTO_INCREMENT PRIMARY KEY,
    refund_id     BIGINT,
    order_item_id BIGINT,
    quantity      INT,
    refund_amount DECIMAL(10, 2)
);
