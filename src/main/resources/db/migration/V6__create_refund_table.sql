CREATE TABLE refund
(
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id      BIGINT,
    order_id     BIGINT,
    reason       VARCHAR(255),
    status       VARCHAR(50),
    requested_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    processed_at DATETIME
);
