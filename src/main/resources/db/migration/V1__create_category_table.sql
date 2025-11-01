CREATE TABLE category (
                          id BIGINT NOT NULL AUTO_INCREMENT,
                          name VARCHAR(255) NOT NULL,
                          description TEXT NULL,
                          parent_id BIGINT NULL COMMENT '부모 카테고리 ID',
                          created_at DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6),
                          updated_at DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
                          PRIMARY KEY (id)
);

CREATE INDEX idx_category_parent_id ON category (parent_id);