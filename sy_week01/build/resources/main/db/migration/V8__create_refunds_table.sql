CREATE TABLE refunds (
     id             BIGINT PRIMARY KEY AUTO_INCREMENT,
     order_id       BIGINT NOT NULL,
     user_id        BIGINT NOT NULL,
     status         ENUM('pending','approved','rejected') NOT NULL DEFAULT 'pending',
     reason         VARCHAR(500),
     approved_by    BIGINT NULL,   -- 관리자 id (users.id 가정)
     approved_at    TIMESTAMP NULL,
     created_at     TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
     updated_at     TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);