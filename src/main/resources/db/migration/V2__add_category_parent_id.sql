-- ============================================
-- V2: categories 테이블에 parent_id 컬럼 추가 (자기 참조 관계)
-- ============================================

-- 부모 카테고리 참조 컬럼 추가
ALTER TABLE categories ADD COLUMN parent_id BIGINT NULL;

-- 외래키 제약조건 추가 (자기 참조)
ALTER TABLE categories
ADD CONSTRAINT fk_categories_parent
FOREIGN KEY (parent_id) REFERENCES categories(id) ON DELETE CASCADE;

-- 인덱스 추가 (부모 카테고리로 검색 성능 향상)
CREATE INDEX idx_categories_parent_id ON categories(parent_id);