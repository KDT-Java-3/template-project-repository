create table refund (
       user_id BIGINT NOT NULL COMMENT '구매한 사용자 ID' PRIMARY KEY,
       purchase_id BIGINT NOT NULL COMMENT '주문 ID' PRIMARY KEY,
       reason text comment '환불 사유',
       status VARCHAR(20) DEFAULT 'PENDING' COMMENT 'PENDING, approved, rejected',
       rg_dt datetime not null default current_timestamp,
       ud_dt datetime not null default current_timestamp ON UPDATE CURRENT_TIMESTAMP
)