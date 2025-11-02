CREATE TABLE refund_items
(
    id            BIGINT PRIMARY KEY AUTO_INCREMENT,
    refund_id     BIGINT         NOT NULL,
    order_item_id BIGINT         NOT NULL,
    quantity      INT            NOT NULL,
    amount        DECIMAL(18, 2) NOT NULL -- 환불 금액(부분 환불 고려)
);