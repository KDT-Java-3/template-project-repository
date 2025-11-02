CREATE TABLE order_shipping (
    id              BIGINT PRIMARY KEY AUTO_INCREMENT,
    order_id        BIGINT NOT NULL UNIQUE,
    recipient_name  VARCHAR(100) NOT NULL,
    phone           VARCHAR(50),
    address_line1   VARCHAR(255) NOT NULL,
    address_line2   VARCHAR(255),
    city            VARCHAR(100),
    state           VARCHAR(100),
    postal_code     VARCHAR(30),
    country         VARCHAR(2) DEFAULT 'KR',
    created_at      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);