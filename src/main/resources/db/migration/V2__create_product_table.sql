CREATE TABLE product (
                      product_id BIGINT NOT NULL AUTO_INCREMENT,
                      name VARCHAR(255),
                      price VARCHAR(20),
                      stock varchar(255),
                      description varchar(255),
                      category_id varchar(255),
                      PRIMARY KEY (product_id)
);