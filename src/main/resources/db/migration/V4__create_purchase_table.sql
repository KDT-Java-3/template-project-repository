create table purchase (
    id binary(16) PRIMARY KEY,
    user_id binary(16) NOT NULL COMMENT '구매한 사용자 ID',
    total_count BIGINT NOT NULL COMMENT '주문한 상품 개수',
    total_price DECIMAL(10, 2) NOT NULL COMMENT '전체 금액',
    status VARCHAR(20) DEFAULT 'PENDING' COMMENT 'PENDING, COMPLETED, CANCELED',
    post_code VARCHAR(6) NOT NULL COMMENT '우편번호',
    recipient_address VARCHAR(255) NOT NULL COMMENT '받는 주소',
    recipient_name VARCHAR(50) NOT NULL COMMENT '받는 사람 이름',
    recipient_phone VARCHAR(11) NOT NULL COMMENT '받는 사람 휴대전화',
    req VARCHAR(100) NOT NULL COMMENT '요청사항',
    rg_dt datetime not null default current_timestamp,
    ud_dt datetime not null default current_timestamp ON UPDATE CURRENT_TIMESTAMP
)