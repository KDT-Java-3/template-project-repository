-- 1. 테스트 사용자 데이터 (5명)
INSERT INTO users (id, email, username, password) VALUES
                                                      (1, 'admin@example.com', 'admin', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy'), -- password: admin123
                                                      (2, 'user1@example.com', 'user1', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy'), -- password: user123
                                                      (3, 'user2@example.com', 'user2', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy'), -- password: user123
                                                      (4, 'seller1@example.com', 'seller1', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy'), -- password: seller123
                                                      (5, 'seller2@example.com', 'seller2', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy'); -- password: seller123

-- 2. 테스트 카테고리 데이터 (계층 구조)
INSERT INTO category (id, name, description, parent_id) VALUES
                                                            (1, '전자제품', '전자제품 카테고리', NULL),
                                                            (2, '의류', '의류 카테고리', NULL),
                                                            (3, '도서', '도서 카테고리', NULL),
                                                            (4, '스마트폰', '스마트폰 및 액세서리', 1),
                                                            (5, '노트북', '노트북 및 컴퓨터', 1),
                                                            (6, '남성의류', '남성 의류', 2),
                                                            (7, '여성의류', '여성 의류', 2),
                                                            (8, '소설', '소설 및 문학', 3),
                                                            (9, '자기계발', '자기계발 도서', 3);

-- 3. 테스트 상품 데이터 (각 카테고리별 2-3개)
INSERT INTO product (id, name, description, price, stock, category_id, user_id) VALUES
-- 전자제품 - 스마트폰
(1, 'iPhone 15 Pro', '최신 애플 스마트폰', 1500000, 50, 4, 4),
(2, 'Galaxy S24 Ultra', '삼성 플래그십 스마트폰', 1400000, 30, 4, 4),
(3, 'Pixel 8 Pro', '구글 최신 스마트폰', 1100000, 20, 4, 5),

-- 전자제품 - 노트북
(4, 'MacBook Pro 14', '애플 M3 Pro 탑재', 2500000, 15, 5, 4),
(5, 'LG 그램 17', '초경량 노트북', 1800000, 25, 5, 5),
(6, 'Dell XPS 15', '프리미엄 윈도우 노트북', 2200000, 10, 5, 4),

-- 의류 - 남성
(7, '남성 정장 셔츠', '고급 면 소재 화이트', 89000, 100, 6, 5),
(8, '남성 청바지', '스트레치 데님', 79000, 80, 6, 5),

-- 의류 - 여성
(9, '여성 원피스', '여름 시즌 플라워 패턴', 129000, 60, 7, 4),
(10, '여성 블라우스', '실크 블렌드 소재', 99000, 70, 7, 4),

-- 도서 - 소설
(11, '해리포터 전집', 'J.K. 롤링 판타지 소설', 150000, 200, 8, 4),
(12, '1984', '조지 오웰의 디스토피아 소설', 12000, 150, 8, 5),

-- 도서 - 자기계발
(13, '아토믹 해빗', '제임스 클리어 자기계발서', 16000, 300, 9, 4),
(14, '데일 카네기 인간관계론', '처세술의 고전', 14000, 250, 9, 5),
(15, '넛지', '리처드 탈러의 행동경제학', 18000, 180, 9, 4);

-- 4. 테스트 주문 데이터 (다양한 상태)
INSERT INTO `order` (id, user_id, product_id, quantity, shipping_address, status) VALUES
-- COMPLETED 주문 (환불 가능한 주문들)
(1, 2, 1, 1, '서울시 강남구 테헤란로 123', 'COMPLETED'),
(2, 2, 11, 2, '서울시 강남구 테헤란로 123', 'COMPLETED'),
(3, 3, 4, 1, '부산시 해운대구 센텀로 456', 'COMPLETED'),
(4, 3, 7, 3, '부산시 해운대구 센텀로 456', 'COMPLETED'),

-- PENDING 주문 (처리 중인 주문들)
(5, 2, 13, 5, '서울시 강남구 테헤란로 123', 'PENDING'),
(6, 3, 9, 2, '부산시 해운대구 센텀로 456', 'PENDING'),
(7, 1, 5, 1, '대전시 유성구 대학로 789', 'PENDING'),

-- CANCELED 주문
(8, 2, 2, 1, '서울시 강남구 테헤란로 123', 'CANCELED');

-- 5. 테스트 환불 데이터 (다양한 상태)
INSERT INTO refund (id, user_id, order_id, reason, status) VALUES
-- PENDING 환불
(1, 2, 1, '상품에 결함이 있습니다. 액정에 얼룩이 있어요.', 'PENDING'),
(2, 3, 4, '사이즈가 맞지 않습니다.', 'PENDING'),

-- APPROVED 환불
(3, 2, 2, '잘못된 상품이 배송되었습니다.', 'APPROVED'),

-- REJECTED 환불
(4, 3, 3, '단순 변심', 'REJECTED');

-- 재고 조정 (환불 승인된 상품의 재고 복원)
-- 주문 2번 (상품 11번, 수량 2개)이 환불 승인되었으므로 재고 복원
UPDATE product SET stock = stock + 2 WHERE id = 11;