CREATE TABLE orders (
    id             BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id        BIGINT NOT NULL,
    status         ENUM('pending','completed','canceled') NOT NULL DEFAULT 'pending',
    total_amount   DECIMAL(18,2) NOT NULL DEFAULT 0.00,
    created_at     TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at     TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);