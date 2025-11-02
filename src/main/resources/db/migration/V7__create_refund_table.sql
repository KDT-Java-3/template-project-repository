CREATE TABLE refund
(
    id            BIGINT         NOT NULL AUTO_INCREMENT,
    order_item_id BIGINT         NOT NULL,
    refund_amount DECIMAL(10, 2) NOT NULL,
    refund_reason VARCHAR(500),
    refund_status VARCHAR(20)    NOT NULL,
    created_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    FOREIGN KEY (order_item_id) REFERENCES order_item (id)
);