CREATE TABLE product (
                         id BIGINT NOT NULL AUTO_INCREMENT,
                         name VARCHAR(255) NOT NULL,
                         description TEXT NULL,
                         price DECIMAL(10, 2) NOT NULL,
                         stock INT NOT NULL DEFAULT 0,
                         category_id BIGINT NOT NULL,
                         created_at DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6),
                         updated_at DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
                         PRIMARY KEY (id)
);

-- 검색/필터링 대비 인덱스
CREATE INDEX idx_product_name ON product (name);
CREATE INDEX idx_product_category_id ON product (category_id);
CREATE INDEX idx_product_price ON product (price);

-- 필요하면 FK도
-- ALTER TABLE product
--   ADD CONSTRAINT fk_product_category
--   FOREIGN KEY (category_id) REFERENCES category(id);
