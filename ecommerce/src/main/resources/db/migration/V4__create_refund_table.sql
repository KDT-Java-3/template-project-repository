CREATE TABLE refund(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    purchase_id BIGINT NOT NULL,
    reason VARCHAR(2000) NOT NULL,
    status VARCHAR(20),
    approved_at DATETIME,
    rejected_at DATETIME,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

create index idx_user_id on refund(user_id);
