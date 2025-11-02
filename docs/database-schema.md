# 데이터베이스 스키마 문서

이 문서는 JPA 엔티티와 데이터베이스 테이블 간의 매핑 정보를 설명합니다.

## MySQL ↔ Java 타입 매핑

| MySQL 타입 | Java 타입 | 설명 |
|-----------|----------|------|
| `BIGINT` | `Long` | 64비트 정수 (-9,223,372,036,854,775,808 ~ 9,223,372,036,854,775,807) |
| `INT` | `Integer` | 32비트 정수 (-2,147,483,648 ~ 2,147,483,647) |
| `VARCHAR(n)` | `String` | 가변 길이 문자열 (최대 n자) |
| `TEXT` | `String` | 긴 텍스트 (최대 65,535자) |
| `DECIMAL(p,s)` | `BigDecimal` | 고정 소수점 숫자 (p: 전체 자릿수, s: 소수점 이하 자릿수) |
| `DATETIME` | `LocalDateTime` | 날짜와 시간 (1000-01-01 00:00:00 ~ 9999-12-31 23:59:59) |
| `BOOLEAN` | `Boolean` | 참/거짓 (MySQL에서는 TINYINT(1)로 저장) |

### 주요 선택 이유
- **BIGINT/Long**: ID 필드는 데이터가 많아질 수 있어서 INT보다 BIGINT 권장
- **DECIMAL/BigDecimal**: 금액 계산 시 부동소수점 오차 방지 (Float/Double 사용 금지)
- **DATETIME/LocalDateTime**: 타임존 없는 날짜/시간 (타임존 필요 시 ZonedDateTime 사용)

---

## Users 테이블

### 개요
- **테이블명**: `users`
- **엔티티 클래스**: `com.example.demo.domain.user.User`
- **마이그레이션 파일**: `V1__init_table.sql`
- **설명**: 사용자 정보를 저장하는 테이블

### 스키마 정의

| 컬럼명 | 데이터 타입 | 제약조건 | JPA 필드 | 설명 |
|--------|------------|---------|----------|------|
| `id` | `BIGINT` | `PRIMARY KEY`, `AUTO_INCREMENT` | `Long id` | 사용자 고유 식별자 |
| `username` | `VARCHAR(50)` | `NOT NULL`, `UNIQUE` | `String username` | 사용자명 (중복 불가) |
| `email` | `VARCHAR(100)` | `NOT NULL`, `UNIQUE` | `String email` | 이메ail 주소 (중복 불가) |
| `password` | `VARCHAR(255)` | `NOT NULL` | `String password` | 암호화된 비밀번호 |
| `created_at` | `DATETIME` | `NOT NULL` | `LocalDateTime createdAt` | 생성 일시 (자동 설정) |
| `updated_at` | `DATETIME` | `NULL` | `LocalDateTime updatedAt` | 수정 일시 (자동 갱신) |

### 인덱스

| 인덱스명 | 타입 | 컬럼 | 용도 |
|---------|------|------|------|
| `PRIMARY` | Primary Key | `id` | 기본키 |
| `uk_users_username` | Unique Key | `username` | 사용자명 중복 방지 및 빠른 조회 |
| `uk_users_email` | Unique Key | `email` | 이메일 중복 방지 및 빠른 조회 |

### 주의사항

1. **비밀번호 저장**: 평문이 아닌 암호화된 형태로 저장해야 합니다 (BCrypt 등 사용)
2. **타임스탬프 수동 설정 금지**: `createdAt`, `updatedAt`은 JPA 라이프사이클 콜백이 자동 관리하므로 수동으로 설정하지 마세요
3. **username/email 중복 체크**: 데이터베이스 레벨에서 UNIQUE 제약조건으로 보장되지만, 애플리케이션 레벨에서도 검증하는 것이 좋습니다
4. **스키마 변경**: Flyway를 사용하므로 스키마 변경 시 새로운 마이그레이션 파일(V3, V4...)을 생성해야 합니다

---

## Categories 테이블

### 개요
- **테이블명**: `categories`
- **엔티티 클래스**: `com.example.demo.domain.category.Category`
- **마이그레이션 파일**: `V1__init_table.sql`, `V2__add_category_parent_id.sql`
- **설명**: 상품 카테고리 정보 (계층 구조 지원)

### 스키마 정의

| 컬럼명 | 데이터 타입 | 제약조건 | JPA 필드 | 설명 |
|--------|------------|---------|----------|------|
| `id` | `BIGINT` | `PRIMARY KEY`, `AUTO_INCREMENT` | `Long id` | 카테고리 고유 식별자 |
| `name` | `VARCHAR(100)` | `NOT NULL`, `UNIQUE` | `String name` | 카테고리명 (중복 불가) |
| `description` | `VARCHAR(500)` | `NULL` | `String description` | 카테고리 설명 |
| `parent_id` | `BIGINT` | `NULL`, `FK` | `Category parent` | 부모 카테고리 ID (자기 참조, 최상위는 NULL) |
| `created_at` | `DATETIME` | `NOT NULL` | `LocalDateTime createdAt` | 생성 일시 |
| `updated_at` | `DATETIME` | `NULL` | `LocalDateTime updatedAt` | 수정 일시 |

### 인덱스 및 외래키
- `PRIMARY`: `id` (기본키)
- `uk_categories_name`: `name` (카테고리명 중복 방지 및 빠른 조회)
- `idx_categories_parent_id`: `parent_id` (부모 카테고리 검색 성능 향상)
- `fk_categories_parent`: `parent_id` → `categories(id)` (자기 참조, ON DELETE CASCADE)

### 관계
- **자기 참조 양방향 관계**: Category ↔ Category (부모-자식 계층 구조)
  - `parent`: 부모 카테고리 (N:1)
  - `children`: 자식 카테고리 목록 (1:N)

### 사용 예시
```java
// 최상위 카테고리
Category electronics = Category.builder()
    .name("전자제품")
    .parent(null)  // 최상위
    .build();

// 하위 카테고리
Category phones = Category.builder()
    .name("휴대폰")
    .parent(electronics)  // 전자제품의 하위
    .build();
```

---

## Products 테이블

### 개요
- **테이블명**: `products`
- **마이그레이션 파일**: `V1__init_table.sql`
- **설명**: 상품 정보

### 스키마 정의

| 컬럼명 | 데이터 타입 | 제약조건 | 설명 |
|--------|------------|---------|------|
| `id` | `BIGINT` | `PRIMARY KEY`, `AUTO_INCREMENT` | 상품 고유 식별자 |
| `category_id` | `BIGINT` | `NOT NULL`, `FK` | 카테고리 ID (외래키) |
| `name` | `VARCHAR(200)` | `NOT NULL` | 상품명 |
| `description` | `TEXT` | `NULL` | 상품 상세 설명 |
| `price` | `DECIMAL(10,2)` | `NOT NULL` | 상품 가격 |
| `stock_quantity` | `INT` | `NOT NULL`, `DEFAULT 0` | 재고 수량 |
| `created_at` | `DATETIME` | `NOT NULL` | 생성 일시 |
| `updated_at` | `DATETIME` | `NULL` | 수정 일시 |

### 인덱스 및 외래키
- `PRIMARY`: `id` (기본키)
- `idx_products_category_id`: `category_id` (카테고리별 상품 조회 성능 향상)
- `fk_products_category`: `category_id` → `categories(id)` (ON DELETE RESTRICT)

---

## Purchases 테이블

### 개요
- **테이블명**: `purchases`
- **마이그레이션 파일**: `V1__init_table.sql`
- **설명**: 구매 주문 정보

### 스키마 정의

| 컬럼명 | 데이터 타입 | 제약조건 | 설명 |
|--------|------------|---------|------|
| `id` | `BIGINT` | `PRIMARY KEY`, `AUTO_INCREMENT` | 구매 고유 식별자 |
| `user_id` | `BIGINT` | `NOT NULL`, `FK` | 사용자 ID (외래키) |
| `total_price` | `DECIMAL(10,2)` | `NOT NULL` | 총 구매 금액 |
| `status` | `VARCHAR(50)` | `NOT NULL`, `DEFAULT 'PENDING'` | 주문 상태 (PENDING, COMPLETED, CANCELLED 등) |
| `created_at` | `DATETIME` | `NOT NULL` | 주문 생성 일시 |
| `updated_at` | `DATETIME` | `NULL` | 주문 수정 일시 |

### 인덱스 및 외래키
- `PRIMARY`: `id` (기본키)
- `idx_purchases_user_id`: `user_id` (사용자별 구매 내역 조회 성능 향상)
- `fk_purchases_user`: `user_id` → `users(id)` (ON DELETE RESTRICT)

---

## Purchase_Products 테이블

### 개요
- **테이블명**: `purchase_products`
- **마이그레이션 파일**: `V1__init_table.sql`
- **설명**: 구매-상품 중간 테이블 (다대다 관계)

### 스키마 정의

| 컬럼명 | 데이터 타입 | 제약조건 | 설명 |
|--------|------------|---------|------|
| `id` | `BIGINT` | `PRIMARY KEY`, `AUTO_INCREMENT` | 레코드 고유 식별자 |
| `purchase_id` | `BIGINT` | `NOT NULL`, `FK` | 구매 ID (외래키) |
| `product_id` | `BIGINT` | `NOT NULL`, `FK` | 상품 ID (외래키) |
| `quantity` | `INT` | `NOT NULL`, `DEFAULT 1` | 구매 수량 |
| `price` | `DECIMAL(10,2)` | `NOT NULL` | 구매 당시 상품 가격 (가격 이력 보존) |
| `created_at` | `DATETIME` | `NOT NULL` | 생성 일시 |

### 인덱스 및 외래키
- `PRIMARY`: `id` (기본키)
- `idx_purchase_products_purchase_id`: `purchase_id` (구매별 상품 조회)
- `idx_purchase_products_product_id`: `product_id` (상품별 구매 이력 조회)
- `fk_purchase_products_purchase`: `purchase_id` → `purchases(id)` (ON DELETE CASCADE)
- `fk_purchase_products_product`: `product_id` → `products(id)` (ON DELETE RESTRICT)

---

## ERD 관계도

```
users (1) ────< (N) purchases (1) ────< (N) purchase_products (N) >──── (1) products
                                                                                ↑
                                                                                |
                                                                          (N) ─┘
                                                                       categories (1)
```

### 관계 설명
- **users ↔ purchases**: 1:N 양방향 (한 사용자는 여러 구매 가능)
- **purchases ↔ purchase_products**: 1:N 양방향 (한 구매는 여러 상품 포함 가능)
- **products ↔ purchase_products**: 1:N 양방향 (한 상품은 여러 구매에 포함 가능)
- **categories ↔ products**: 1:N 단방향 (한 카테고리는 여러 상품 포함)
- **categories ↔ categories**: 자기 참조 양방향 (부모-자식 계층 구조)

---

## 변경 이력

| 버전 | 날짜 | 변경 내용 | 마이그레이션 파일 |
|------|------|-----------|------------------|
| V1 | 2025-11-01 | 초기 스키마 생성 (users, categories, products, purchases, purchase_products) | `V1__init_table.sql` |
| V2 | 2025-11-01 | categories 테이블에 parent_id 컬럼 추가 (계층 구조 지원) | `V2__add_category_parent_id.sql` |
