-- 샘플 사용자 2명 추가
INSERT INTO users (username, email, password_hash, address, status, created_at, updated_at)
VALUES
    ('kim_user', 'kim@example.com', '$2a$10$dummyHashForUser1', '서울시 강남구 테헤란로 123', 'ACTIVE', NOW(), NOW()),
    ('lee_user', 'lee@example.com', '$2a$10$dummyHashForUser2', '서울시 서초구 서초대로 456', 'ACTIVE', NOW(), NOW());
