CREATE TABLE users (
    id bigint not null auto_increment,
    primary key(id)
);

CREATE TABLE category (
    id bigint not null auto_increment,
    name varchar(255) not null,
    description text,
    primary key (id)
);

CREATE TABLE product (
    id bigint not null auto_increment,
    name varchar(255) not null,
    price decimal(10, 2) not null,
    stock int not null default 0,
    category_id bigint not null,
    description text,
    primary key (id)
);

CREATE TABLE orders (
    id bigint not null auto_increment,
    user_id bigint not null,
    shipping_address varchar(255) not null,
    status enum('pending', 'completed', 'canceled') not null default 'pending',
    order_date datetime,
    primary key(id)
);

CREATE TABLE order_product (
    id bigint not null auto_increment,
    order_id bigint not null,
    product_id bigint not null,
    quantity int not null,
    price decimal(10, 2) not null,
    primary key(id)
);


CREATE TABLE refund (
    id bigint not null auto_increment,
    user_id bigint not null,
    order_id bigint not null,
    reason text not null,
    refund_date datetime,
    status enum('pending', 'approved', 'rejected') not null default 'pending',
    primary key(id)
);
