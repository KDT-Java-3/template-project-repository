-- 카테고리 테이블에 description 컬럼 추가
ALTER TABLE category
    ADD COLUMN description VARCHAR(500) AFTER name;

