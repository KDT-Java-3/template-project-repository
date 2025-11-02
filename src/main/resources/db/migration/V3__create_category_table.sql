CREATE TABLE category (
                         category_id BIGINT NOT NULL AUTO_INCREMENT,
                         name VARCHAR(255),
                         parent BIGINT,
                         PRIMARY KEY (category_id)
);