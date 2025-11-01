create table product(
        id binary(16) primary key,
        name varchar(255) not null,
        description text comment '설명',
        price DECIMAL(10, 2) NOT NULL comment '가격',
        stock INT NOT NULL DEFAULT 0 comment '수량',
        category_id binary(16) not null comment '상품이 속한 카테고리 ID',
        rg_dt datetime not null default current_timestamp,
        ud_dt datetime not null default current_timestamp ON UPDATE CURRENT_TIMESTAMP
)