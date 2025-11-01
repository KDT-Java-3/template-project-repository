CREATE TABLE purchase_product
( -- 단수형으로 이름 변경
    id          binary(16) PRIMARY KEY,
    purchase_id binary(16)         NOT NULL COMMENT '어떤 주문에 속하는지',
    product_id  binary(16)         NOT NULL COMMENT '어떤 상품인지',
    quantity    INT            NOT NULL comment '수량',
    price       DECIMAL(10, 2) NOT NULL COMMENT '주문 시점의 상품 가격',
    rg_dt datetime not null default current_timestamp,
    ud_dt datetime not null default current_timestamp ON UPDATE CURRENT_TIMESTAMP
);