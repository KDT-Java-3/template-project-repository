-- V3: purchases 테이블에 shipping_address 컬럼 추가

ALTER TABLE purchases
ADD COLUMN shipping_address VARCHAR(500);