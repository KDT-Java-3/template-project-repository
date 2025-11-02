CREATE TABLE categories (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(500)
);

CREATE TABLE members (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id VARCHAR(50) NOT NULL UNIQUE,
    username VARCHAR(30) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE products (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(200) NOT NULL,
    price INT NOT NULL,
    stock INT NOT NULL,
    category_id BIGINT NOT NULL,
    FOREIGN KEY (category_id) REFERENCES categories(id)
);

CREATE TABLE orders (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    member_id BIGINT,
    quantity INT,
    shipping_address VARCHAR(255),
    order_status VARCHAR(20),
    FOREIGN KEY (member_id) REFERENCES members(id)
);

-- Join table for orders and products (unidirectional @OneToMany)
CREATE TABLE orders_products (
    order_id BIGINT NOT NULL,
    products_id BIGINT NOT NULL,
    PRIMARY KEY (order_id, products_id),
    FOREIGN KEY (order_id) REFERENCES orders(id),
    FOREIGN KEY (products_id) REFERENCES products(id)
);

CREATE TABLE refunds (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    member_id BIGINT,
    order_id BIGINT,
    reason VARCHAR(255),
    refund_status VARCHAR(20),
    FOREIGN KEY (member_id) REFERENCES members(id),
    FOREIGN KEY (order_id) REFERENCES orders(id)
);
