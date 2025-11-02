create table user (
    id bigint auto_increment primary key,
    name varchar(255) not null,
    email varchar(255) not null unique,
    phone_number varchar(20) not null
);

create table product (
    id bigint auto_increment primary key,
    name varchar(255) not null,
    description text,
    price int not null,
    stock int not null,
    category_id bigint not null,
    created_at datetime default current_timestamp,
    updated_at datetime default current_timestamp on update current_timestamp
);

create table category (
    id bigint auto_increment primary key,
    name varchar(255) not null,
    description text,
    parent_id bigint,
    created_at datetime default current_timestamp,
    updated_at datetime default current_timestamp on update current_timestamp
);

create table orders (
    id bigint auto_increment primary key,
    user_id bigint not null,
    shipping_address varchar(255) not null,
    order_status enum('pending', 'completed', 'canceled') not null,
    create_at datetime default current_timestamp,
    updated_at datetime default current_timestamp on update current_timestamp
);

create table orders_detail (
    id bigint auto_increment primary key,
    order_id bigint not null,
    product_id bigint not null,
    quantity int not null,
    create_at datetime default current_timestamp,
    updated_at datetime default current_timestamp on update current_timestamp
);

create table refund (
    id bigint auto_increment primary key,
    order_id bigint not null,
    reason text not null,
    refund_status enum('pending', 'approved', 'rejected') not null,
    create_at datetime default current_timestamp,
    updated_at datetime default current_timestamp on update current_timestamp
)