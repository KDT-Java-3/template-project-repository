-- Insert Sample Users
INSERT INTO user (name, email, phone) VALUES
('홍길동', 'hong@example.com', '010-1234-5678'),
('김철수', 'kim@example.com', '010-2345-6789'),
('이영희', 'lee@example.com', '010-3456-7890');

-- Insert Sample Categories
INSERT INTO category (name, description) VALUES
('전자기기', '스마트폰, 태블릿, 노트북 등'),
('의류', '남성복, 여성복, 아동복'),
('식품', '신선식품, 가공식품, 음료'),
('도서', '소설, 에세이, 자기계발서');

-- Insert Sample Products
INSERT INTO product (name, description, price, stock, category_id) VALUES
('iPhone 15 Pro', '최신 애플 스마트폰', 1350000.00, 50, 1),
('Galaxy S24', '삼성 플래그십 모델', 1200000.00, 30, 1),
('MacBook Pro 14', 'M3 프로세서 탑재', 2500000.00, 20, 1),
('나이키 에어맥스', '편안한 운동화', 150000.00, 100, 2),
('유니클로 티셔츠', '기본 면 티셔츠', 15000.00, 200, 2),
('유기농 사과', '신선한 국내산 사과 1kg', 8000.00, 500, 3),
('청정원 고추장', '전통 발효 고추장 500g', 5500.00, 300, 3),
('해리포터 전집', 'J.K. 롤링 판타지 소설', 85000.00, 50, 4);
