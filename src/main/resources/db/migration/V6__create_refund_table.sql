create table refund (
   id binary(16) PRIMARY KEY,
   purchase_id binary(16) NOT NULL COMMENT '주문 ID',
   reason text comment '환불 사유',
   status VARCHAR(20) DEFAULT 'PENDING' COMMENT 'PENDING, approved, rejected',
   rg_dt datetime not null default current_timestamp,
   ud_dt datetime not null default current_timestamp ON UPDATE CURRENT_TIMESTAMP
   #PRIMARY KEY(user_id, purchase_id)
)