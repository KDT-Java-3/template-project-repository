-- commerce db
-- user table
create table user
(
    id            bigint auto_increment primary key,
    username      varchar(50)  not null,
    email         varchar(255) not null unique,
    password_hash varchar(255) not null,
    role_id varchar(255) not null,      -- 상품등록자 or 구매자
    created_at    datetime default current_timestamp,
    updated_at    datetime default current_timestamp on update current_timestamp
);

-- category table
create table category
(
    id         bigint auto_increment primary key,
    name       varchar(255) not null,
    parent_id  bigint default null comment '부모 카테고리 id (자기 참조)',
    description text,
    created_at datetime default current_timestamp,
    updated_at datetime default current_timestamp on update current_timestamp
);

-- 상품재고 관리 : product table
create table product
(
    id          bigint auto_increment primary key,
    name        varchar(255)   not null,
    description text,
    price       decimal(10, 2) not null,
    stock       int            not null default 0,
    category_id bigint comment '상품이 속한 카테고리 id',
    created_at  datetime                default current_timestamp,
    updated_at  datetime                default current_timestamp on update current_timestamp,
    foreign key (category_id) references category(id)
);

-- purchase table
create table purchase
(
    id              bigint auto_increment primary key,
    user_id         bigint         not null comment '구매한 사용자 id',
    total_price     decimal(10, 2) not null,
    status          varchar(20) default 'pending' comment '주문상태, 값 : pending, completed, canceled',
#     shipping_address text           not null,
    ship_post_code varchar(6) not null comment '구매자 우편번호',
    ship_address varchar(255) not null comment '구매자 주소',
    ship_address_detail varchar(255) not null comment '구매자 상세주소',
    ship_name varchar(50) not null comment '수신자 성명',
    ship_phone_number varchar(20) not null comment '수신자 휴대폰번호',
    created_at       datetime(6) default current_timestamp(6),
    updated_at       datetime(6) default current_timestamp(6) on update current_timestamp(6),
    foreign key (user_id) references user(id)
);

-- purchase_items table
create table purchase_product
(
    id          bigint auto_increment primary key,
    purchase_id bigint         not null comment '어떤 주문에 속하는지',
    product_id  bigint         not null comment '어떤 상품인지',
    quantity    int            not null,
    price       decimal(10, 2) not null comment '주문 시점의 상품 가격',
    created_at  datetime(6) default current_timestamp(6),
    updated_at  datetime(6) default current_timestamp(6) on update current_timestamp(6),
    foreign key (purchase_id) references purchase(id),
    foreign key (product_id) references product(id)
);

-- 장바구니 : shopping_cart table
-- 로그인한 사용자가 장바구니에 넣은 상품 정보
create table shopping_cart (
    id           bigint auto_increment primary key,
    user_id      bigint         not null comment '어떤 사용자의 장바구니에 속하는지',
    product_id   bigint         not null comment '어떤 상품인지',
    quantity     int            not null,
    price        decimal(10, 2) not null comment '현재 시점의 상품 가격',
    created_at   datetime(6) default current_timestamp(6),
    updated_at   datetime(6) default current_timestamp(6) on update current_timestamp(6),
    foreign key (user_id) references user(id),
    foreign key (product_id) references product(id)
);

-- 환불관리 : refund table
create table refund (
    id          bigint auto_increment primary key,
    user_id     bigint         not null comment '어떤 사용자의 주문에 속하는지',
    purchase_id   bigint         not null comment '어떤 주문에 속하는지',
    reason      text           not null,
    refund_status   varchar(20) default 'pending' comment '환불상태, 값 : pending, approved, rejected',
    created_at  datetime(6) default current_timestamp(6),
    updated_at  datetime(6) default current_timestamp(6) on update current_timestamp(6),
    foreign key (user_id) references user(id),
    foreign key (purchase_id) references purchase(id)
);
