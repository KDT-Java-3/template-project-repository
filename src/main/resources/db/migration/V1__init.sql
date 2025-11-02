CREATE TABLE users
(
    id            BIGINT       NOT NULL AUTO_INCREMENT PRIMARY KEY,
    username      VARCHAR(50)  NOT NULL,
    nickname      VARCHAR(50)  NULL,
    email         VARCHAR(255) NOT NULL UNIQUE,
    role          VARCHAR(20)  NOT NULL DEFAULT 'USER',
    password_hash VARCHAR(255) NOT NULL,
    created_at    DATETIME              DEFAULT CURRENT_TIMESTAMP,
    updated_at    DATETIME              DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE category
(
    id          BIGINT       NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(50)  NOT NULL,
    description VARCHAR(255) NULL,
    parent_id   BIGINT       NULL COMMENT '부모 카테고리 ID (자기참조)',
    created_at  DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at  DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE product
(
    id          BIGINT         NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(50)    NOT NULL,
    price       DECIMAL(10, 2) NOT NULL DEFAULT 0,
    description MEDIUMTEXT     NULL,
    stock       INTEGER        NOT NULL,
    category_id BIGINT         NULL,
    created_at  DATETIME                DEFAULT CURRENT_TIMESTAMP,
    updated_at  DATETIME                DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (category_id) REFERENCES category (id)
);

CREATE TABLE orders
(
    id               BIGINT         NOT NULL AUTO_INCREMENT PRIMARY KEY,
    user_id          BIGINT         NOT NULL,
    total_price      DECIMAL(10, 2) NOT NULL,
    shipping_address MEDIUMTEXT     NOT NULL,
    status           VARCHAR(50)    NOT NULL DEFAULT 'PENDING',
    created_at       DATETIME                DEFAULT CURRENT_TIMESTAMP,
    updated_at       DATETIME                DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE refund
(
    id          BIGINT      NOT NULL AUTO_INCREMENT PRIMARY KEY,
    order_id    BIGINT      NOT NULL,
    user_id     BIGINT      NOT NULL,
    status      VARCHAR(50) NOT NULL DEFAULT 'PENDING',
    reason      MEDIUMTEXT  NULL,
    created_at  DATETIME                DEFAULT CURRENT_TIMESTAMP,
    updated_at  DATETIME                DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    approved_at DATETIME    NULL,
    rejected_at DATETIME    NULL,
    FOREIGN KEY (order_id) REFERENCES orders (id),
    FOREIGN KEY (user_id) REFERENCES users (id)
);


CREATE TABLE product_order
(
    id         BIGINT         NOT NULL AUTO_INCREMENT PRIMARY KEY,
    product_id BIGINT         NOT NULL,
    order_id   BIGINT         NOT NULL,
    quantity   INTEGER        NOT NULL,
    price      DECIMAL(10, 2) NOT NULL,
    created_at  DATETIME                DEFAULT CURRENT_TIMESTAMP,
    updated_at  DATETIME                DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (product_id) REFERENCES product (id),
    FOREIGN KEY (order_id) REFERENCES orders (id)
)





