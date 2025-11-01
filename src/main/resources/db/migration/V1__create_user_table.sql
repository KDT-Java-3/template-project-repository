create table user
(
    id binary(16) PRIMARY KEY,
    name varchar(50) not NULL COMMENT '사용자 이름',
    registration_number VARCHAR(13) not null unique COMMENT '등록번호(주민,외국인)',
    birth VARCHAR(8) not null unique COMMENT '사용자 생일',
    email varchar(255) null unique COMMENT '사용자 eamil',
    phone VARCHAR(11) null unique COMMENT '사용자 핸드폰 번호',
    is_foreigner boolean not null default false COMMENT '외국인여부',
    login_id varchar(30) not null unique COMMENT '사용자 loginId',
    #password_hash VARCHAR(255) NOT NULL COMMENT '사용자 login password 별도 member table 생성하여 로그인 및 권환에 대한 작업(관리자,사용자,판매자)',
    rg_dt datetime not null default current_timestamp,
    ud_dt datetime not null default current_timestamp ON UPDATE CURRENT_TIMESTAMP
)