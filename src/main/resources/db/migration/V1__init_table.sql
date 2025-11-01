CREATE TABLE users (
                         id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                         username VARCHAR(15) NOT NULL,
                         password_hash VARCHAR(255) NOT NULL,
                         name VARCHAR(20) NOT NULL,
                         email VARCHAR(255) NOT NULL UNIQUE,
                         created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                         updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE product (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         name VARCHAR(50) NOT NULL,
                         description TEXT,
                         price DECIMAL(10, 2) NOT NULL,
                         stock INT NOT NULL DEFAULT 0,
                         category_id BIGINT COMMENT '상품이 속한 카테고리 ID',
                         created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                         updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE category (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          name VARCHAR(10) NOT NULL,
                          description TEXT,
                          parent_id BIGINT DEFAULT NULL COMMENT '부모 카테고리 ID (자기 참조)',
                          created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                          updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE orders (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          user_id BIGINT NOT NULL COMMENT '구매한 사용자 ID',
                          total_price DECIMAL(10, 2) NOT NULL,
                          status VARCHAR(20) DEFAULT 'PENDING' COMMENT 'PENDING, COMPLETED, CANCELED',
                          shipping_address TEXT NOT NULL,
                          created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                          updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE order_product (
                                  id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                  order_id BIGINT NOT NULL COMMENT '어떤 주문에 속하는지',
                                  product_id BIGINT NOT NULL COMMENT '어떤 상품인지',
                                  quantity INT NOT NULL,
                                  price DECIMAL(10, 2) NOT NULL COMMENT '주문 시점의 상품 가격',
                                  created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                                  updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE order_product_refund (
                                      id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                      order_product_id BIGINT NOT NULL,
                                      quantity INT,
                                      status VARCHAR(50) DEFAULT 'PENDING' COMMENT 'PENDING, APPROVED, REJECTED',
                                      price DECIMAL(10, 2) NOT NULL,
                                      reason TEXT,
                                      created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                                      updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);