CREATE table category (
    id binary(16) NOT NULL primary key,
    name varchar(255) not null,
    description text comment '설명',
    parent_id binary(16) default null comment 'FK: 부모 카테고리의 id를 참조',
    rg_dt datetime not null default current_timestamp,
    ud_dt datetime not null default current_timestamp ON UPDATE CURRENT_TIMESTAMP
)