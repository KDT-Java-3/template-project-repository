create table products (
    id BIGINT NOT NULL AUTO_INCREMENT,
    name varchar(255),
    price INT,
    stock INT,
    category_id BIGINT,
    PRIMARY KEY (id),
    FOREIGN KEY (category_id) REFERENCES categories(id)
);