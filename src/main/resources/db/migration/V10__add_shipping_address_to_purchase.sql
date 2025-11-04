-- 주문 테이블에 배송 주소 컬럼 추가
ALTER TABLE purchase
    ADD COLUMN shipping_address VARCHAR(500) AFTER total_price;

