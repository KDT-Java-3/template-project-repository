create table user
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name varchar(50) not NULL,
    registration_number VARCHAR(13) not null unique,
    birth VARCHAR(8) not null unique,
    email varchar(255) null unique,
    phone VARCHAR(11) null unique,
    foreigner boolean not null default false,
    member_id varchar(30) not null unique,
    password_hash VARCHAR(255) NOT NULL,
    rg_dt datetime not null default current_timestamp,
    ud_dt datetime not null default current_timestamp ON UPDATE CURRENT_TIMESTAMP
)