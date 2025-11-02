-- 1) 기본 카테고리 생성(없으면)
INSERT INTO category (name, description)
SELECT 'Uncategorized', '미지정/기타 카테고리'
    WHERE NOT EXISTS (SELECT 1 FROM category WHERE name = 'Uncategorized');

-- 2) NULL 백필
-- UPDATE product
-- SET category_id = (SELECT id FROM category WHERE name = 'Uncategorized' LIMIT 1)
-- WHERE category_id IS NULL;

-- 3) NOT NULL로 변경
ALTER TABLE product
    MODIFY category_id BIGINT NOT NULL;