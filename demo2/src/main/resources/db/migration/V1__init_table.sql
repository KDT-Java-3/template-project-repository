CREATE TABLE category
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    name       VARCHAR(255) NOT NULL,
    parent_id  BIGINT NULL,
    created_at DATETIME     NOT NULL,
    updated_at DATETIME     NOT NULL,
    CONSTRAINT pk_category PRIMARY KEY (id)
);

CREATE TABLE product
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    category_id   BIGINT NULL,
    name          VARCHAR(255) NOT NULL,
    `DESCRIPTION` TEXT NULL,
    price         DECIMAL      NOT NULL,
    stock         INT          NOT NULL,
    created_at    DATETIME     NOT NULL,
    updated_at    DATETIME     NOT NULL,
    CONSTRAINT pk_product PRIMARY KEY (id)
);

CREATE TABLE purchase
(
    id          BIGINT AUTO_INCREMENT NOT NULL,
    user_id     BIGINT      NOT NULL,
    total_price DECIMAL     NOT NULL,
    status      VARCHAR(20) NOT NULL,
    created_at  DATETIME    NOT NULL,
    updated_at  DATETIME    NOT NULL,
    CONSTRAINT pk_purchase PRIMARY KEY (id)
);

CREATE TABLE purchase_product
(
    id          BIGINT AUTO_INCREMENT NOT NULL,
    purchase_id BIGINT   NOT NULL,
    product_id  BIGINT   NOT NULL,
    quantity    INT      NOT NULL,
    price       DECIMAL  NOT NULL,
    created_at  DATETIME NOT NULL,
    updated_at  DATETIME NOT NULL,
    CONSTRAINT pk_purchaseproduct PRIMARY KEY (id)
);

CREATE TABLE users
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    usernname     VARCHAR(50)  NOT NULL,
    email         VARCHAR(255) NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    status        VARCHAR(1)   NOT NULL,
    created_at    DATETIME NULL,
    updated_at    VARCHAR(255) NULL,
    CONSTRAINT pk_users PRIMARY KEY (id)
);

ALTER TABLE users
    ADD CONSTRAINT uc_users_email UNIQUE (email);

ALTER TABLE users
    ADD CONSTRAINT uc_users_usernname UNIQUE (usernname);

ALTER TABLE category
    ADD CONSTRAINT fk_category_on_parent FOREIGN KEY (parent_id) REFERENCES category (id);

ALTER TABLE product
    ADD CONSTRAINT fk_product_on_category FOREIGN KEY (category_id) REFERENCES category (id);

ALTER TABLE purchase_product
    ADD CONSTRAINT fk_purchaseproduct_on_product FOREIGN KEY (product_id) REFERENCES product (id);

ALTER TABLE purchase_product
    ADD CONSTRAINT fk_purchaseproduct_on_purchase FOREIGN KEY (purchase_id) REFERENCES purchase (id);

ALTER TABLE purchase
    ADD CONSTRAINT fk_purchase_on_user FOREIGN KEY (user_id) REFERENCES users (id);