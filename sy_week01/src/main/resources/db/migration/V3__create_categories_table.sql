CREATE TABLE categories (
        id            BIGINT PRIMARY KEY AUTO_INCREMENT,
        parent_id     BIGINT NULL,
        name          VARCHAR(100) NOT NULL,
        description   VARCHAR(500),
        created_at    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
        updated_at    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);