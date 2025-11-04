CREATE TABLE refund
(
    id          BIGINT       NOT NULL AUTO_INCREMENT,
    purchase_id BIGINT       NOT NULL,
    user_id     BIGINT       NOT NULL,
    reason      VARCHAR(500) NOT NULL,
    status      VARCHAR(20)  NOT NULL DEFAULT 'PENDING',
    created_at  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    FOREIGN KEY (purchase_id) REFERENCES purchase(id),
    FOREIGN KEY (user_id) REFERENCES user(id),
    INDEX idx_purchase_id (purchase_id),
    INDEX idx_user_id (user_id),
    INDEX idx_status (status)
);

