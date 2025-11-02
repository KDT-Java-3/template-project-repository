-- 카테고리 테이블
CREATE TABLE IF NOT EXISTS category (
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(120) NOT NULL,
    description VARCHAR(1000),
    PRIMARY KEY (id),
    UNIQUE KEY uq_category_name (name)
    ) ENGINE=InnoDB;

-- product에 FK 컬럼 추가
ALTER TABLE product
    ADD COLUMN category_id BIGINT NULL;

-- 인덱스 생성
CREATE INDEX idx_product_category ON product(category_id);

-- FK 추가
ALTER TABLE product
    ADD CONSTRAINT fk_product_category
        FOREIGN KEY (category_id) REFERENCES category(id);