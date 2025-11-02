-- 이 마이그레이션은 서비스 운영 중 사용자 관리 기능이 필요해지면서 추가되었습니다
ALTER TABLE users ADD COLUMN status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE';
