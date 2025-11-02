CREATE TABLE products (
      id            BIGINT PRIMARY KEY AUTO_INCREMENT,
      category_id   BIGINT NOT NULL,
      name          VARCHAR(200) NOT NULL,
      description   TEXT,
      price         DECIMAL(18,2) NOT NULL,
      stock         INT NOT NULL DEFAULT 0,
      is_active     TINYINT(1) NOT NULL DEFAULT 1,
      version       BIGINT NOT NULL DEFAULT 0, -- JPA @Version(낙관적 락)
      created_at    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
      updated_at    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);