-- 테이블 생성 후, 수정 사항이 발생하였을 경우를 가정하여 status 컬럼을 추가합니다.
ALTER TABLE user
    ADD COLUMN status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE' AFTER role;

-- status 컬럼에 대한 인덱스 추가
CREATE INDEX idx_status ON user(status);

