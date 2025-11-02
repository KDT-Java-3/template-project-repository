# 커머스 API 문서

## 📋 목차
- [프로젝트 개요](#프로젝트-개요)
- [기술 스택](#기술-스택)
- [데이터베이스 스키마](#데이터베이스-스키마)
- [API 엔드포인트](#api-엔드포인트)
  - [카테고리 API](#1-카테고리-api)
  - [상품 API](#2-상품-api)
  - [주문 API](#3-주문-api)
  - [환불 API](#4-환불-api)
- [에러 처리](#에러-처리)

---

## 프로젝트 개요

실제 커머스 서비스에서 활용될 수 있는 RESTful API를 구현한 프로젝트입니다.

### 주요 기능
- ✅ 카테고리 관리 (등록, 조회, 수정)
- ✅ 상품 관리 (등록, 조회, 수정, 검색)
- ✅ 주문 관리 (생성, 조회, 상태 변경, 취소)
- ✅ 환불 관리 (요청, 조회, 승인/거절)

---

## 기술 스택

- **Java** 21
- **Spring Boot** 3.5.7
- **Spring Data JPA** (Hibernate)
- **MySQL** 8.0
- **Flyway** (DB Migration)
- **Lombok**
- **MapStruct**
- **Swagger/OpenAPI** 3.0

---

## 데이터베이스 스키마

### ERD 주요 테이블

#### 1. categories (카테고리)
```sql
id            BIGINT (PK)
name          VARCHAR(100)
description   TEXT
created_at    TIMESTAMP
updated_at    TIMESTAMP
```

#### 2. products (상품)
```sql
id            BIGINT (PK)
name          VARCHAR(200)
description   TEXT
price         DECIMAL(10,2)
stock         INT
category_id   BIGINT (FK)
created_at    TIMESTAMP
updated_at    TIMESTAMP
```

#### 3. orders (주문)
```sql
id                BIGINT (PK)
user_id           BIGINT
status            VARCHAR(20) - PENDING, COMPLETED, CANCELED
shipping_address  TEXT
order_date        TIMESTAMP
created_at        TIMESTAMP
updated_at        TIMESTAMP
```

#### 4. order_items (주문 상품)
```sql
id          BIGINT (PK)
order_id    BIGINT (FK)
product_id  BIGINT (FK)
quantity    INT
price       DECIMAL(10,2)
created_at  TIMESTAMP
updated_at  TIMESTAMP
```

#### 5. refunds (환불)
```sql
id            BIGINT (PK)
order_id      BIGINT (FK)
user_id       BIGINT
reason        TEXT
status        VARCHAR(20) - PENDING, APPROVED, REJECTED
request_date  TIMESTAMP
created_at    TIMESTAMP
updated_at    TIMESTAMP
```

---

## API 엔드포인트

### Base URL
```
http://localhost:8080/api
```

### Swagger UI
```
http://localhost:8080/swagger-ui.html
```

---

## 1. 카테고리 API

### 1.1 카테고리 등록
**POST** `/api/categories`

**Request Body:**
```json
{
  "name": "전자제품",
  "description": "전자제품 카테고리"
}
```

**Response:** `201 Created`
```json
{
  "id": 1,
  "name": "전자제품",
  "description": "전자제품 카테고리",
  "createdAt": "2025-11-01T10:00:00",
  "updatedAt": "2025-11-01T10:00:00"
}
```

---

### 1.2 카테고리 전체 조회
**GET** `/api/categories`

**Response:** `200 OK`
```json
[
  {
    "id": 1,
    "name": "전자제품",
    "description": "전자제품 카테고리",
    "createdAt": "2025-11-01T10:00:00",
    "updatedAt": "2025-11-01T10:00:00"
  }
]
```

---

### 1.3 카테고리 단건 조회
**GET** `/api/categories/{id}`

**Response:** `200 OK`

---

### 1.4 카테고리 수정
**PUT** `/api/categories/{id}`

**Request Body:**
```json
{
  "name": "가전제품",
  "description": "가전제품 카테고리"
}
```

**Response:** `200 OK`

---

### 1.5 카테고리 삭제
**DELETE** `/api/categories/{id}`

**Response:** `204 No Content`

---

## 2. 상품 API

### 2.1 상품 등록
**POST** `/api/products`

**Request Body:**
```json
{
  "name": "삼성 갤럭시 S24",
  "description": "최신 스마트폰",
  "price": 1200000,
  "stock": 100,
  "categoryId": 1
}
```

**Response:** `201 Created`
```json
{
  "id": 1,
  "name": "삼성 갤럭시 S24",
  "description": "최신 스마트폰",
  "price": 1200000,
  "stock": 100,
  "categoryId": 1,
  "categoryName": "전자제품",
  "createdAt": "2025-11-01T10:00:00",
  "updatedAt": "2025-11-01T10:00:00"
}
```

---

### 2.2 상품 전체 조회
**GET** `/api/products`

**Response:** `200 OK`

---

### 2.3 상품 단건 조회
**GET** `/api/products/{id}`

**Response:** `200 OK`

---

### 2.4 상품 검색
**GET** `/api/products/search`

**Query Parameters:**
- `categoryId` (선택): 카테고리 ID
- `minPrice` (선택): 최소 가격
- `maxPrice` (선택): 최대 가격
- `keyword` (선택): 상품명 키워드

**Example:**
```
GET /api/products/search?categoryId=1&minPrice=1000000&maxPrice=2000000&keyword=갤럭시
```

**Response:** `200 OK`

---

### 2.5 상품 수정
**PUT** `/api/products/{id}`

**Request Body:**
```json
{
  "name": "삼성 갤럭시 S24 Ultra",
  "description": "최신 프리미엄 스마트폰",
  "price": 1500000,
  "stock": 50,
  "categoryId": 1
}
```

**Response:** `200 OK`

---

### 2.6 상품 삭제
**DELETE** `/api/products/{id}`

**Response:** `204 No Content`

---

## 3. 주문 API

### 3.1 주문 생성
**POST** `/api/orders`

**Request Body:**
```json
{
  "userId": 1,
  "shippingAddress": "서울시 강남구 테헤란로 123",
  "orderItems": [
    {
      "productId": 1,
      "quantity": 2
    },
    {
      "productId": 2,
      "quantity": 1
    }
  ]
}
```

**Response:** `201 Created`
```json
{
  "id": 1,
  "userId": 1,
  "status": "PENDING",
  "shippingAddress": "서울시 강남구 테헤란로 123",
  "orderDate": "2025-11-01T10:00:00",
  "orderItems": [
    {
      "id": 1,
      "productId": 1,
      "productName": "삼성 갤럭시 S24",
      "quantity": 2,
      "price": 1200000
    }
  ],
  "createdAt": "2025-11-01T10:00:00",
  "updatedAt": "2025-11-01T10:00:00"
}
```

**비즈니스 로직:**
- 주문 생성 시 자동으로 상품 재고 감소
- 재고 부족 시 에러 반환

---

### 3.2 주문 단건 조회
**GET** `/api/orders/{id}`

**Response:** `200 OK`

---

### 3.3 사용자별 주문 조회
**GET** `/api/orders/user/{userId}`

**Response:** `200 OK`

---

### 3.4 사용자별 주문 상태 조회
**GET** `/api/orders/user/{userId}/status/{status}`

**Path Parameters:**
- `status`: PENDING, COMPLETED, CANCELED

**Example:**
```
GET /api/orders/user/1/status/PENDING
```

**Response:** `200 OK`

---

### 3.5 주문 상태 변경
**PATCH** `/api/orders/{id}/status`

**Query Parameters:**
- `status`: PENDING, COMPLETED, CANCELED

**Example:**
```
PATCH /api/orders/1/status?status=COMPLETED
```

**Response:** `200 OK`

---

### 3.6 주문 취소
**DELETE** `/api/orders/{id}`

**Response:** `204 No Content`

**비즈니스 로직:**
- PENDING 상태의 주문만 취소 가능
- 취소 시 상품 재고 복원

---

## 4. 환불 API

### 4.1 환불 요청
**POST** `/api/refunds`

**Request Body:**
```json
{
  "userId": 1,
  "orderId": 1,
  "reason": "단순 변심"
}
```

**Response:** `201 Created`
```json
{
  "id": 1,
  "orderId": 1,
  "userId": 1,
  "reason": "단순 변심",
  "status": "PENDING",
  "requestDate": "2025-11-01T10:00:00",
  "createdAt": "2025-11-01T10:00:00",
  "updatedAt": "2025-11-01T10:00:00"
}
```

---

### 4.2 환불 단건 조회
**GET** `/api/refunds/{id}`

**Response:** `200 OK`

---

### 4.3 사용자별 환불 조회
**GET** `/api/refunds/user/{userId}`

**Response:** `200 OK`

---

### 4.4 사용자별 환불 상태 조회
**GET** `/api/refunds/user/{userId}/status/{status}`

**Path Parameters:**
- `status`: PENDING, APPROVED, REJECTED

**Example:**
```
GET /api/refunds/user/1/status/PENDING
```

**Response:** `200 OK`

---

### 4.5 환불 승인
**PATCH** `/api/refunds/{id}/approve`

**Response:** `200 OK`

**비즈니스 로직:**
- PENDING 상태의 환불만 승인 가능
- 승인 시 상품 재고 복원

---

### 4.6 환불 거절
**PATCH** `/api/refunds/{id}/reject`

**Response:** `200 OK`

**비즈니스 로직:**
- PENDING 상태의 환불만 거절 가능

---

## 에러 처리

### 공통 에러 응답 형식

```json
{
  "message": "에러 메시지",
  "status": 404,
  "timestamp": "2025-11-01T10:00:00"
}
```

### 주요 에러 코드

| HTTP Status | 설명 | 예시 |
|------------|------|------|
| 400 Bad Request | 잘못된 요청 | 재고 부족, 유효성 검증 실패 |
| 404 Not Found | 리소스 없음 | 존재하지 않는 상품/주문 |
| 500 Internal Server Error | 서버 오류 | 예상치 못한 에러 |

### 에러 예시

#### 1. 리소스 없음
```json
{
  "message": "상품을 찾을 수 없습니다. (ID: 999)",
  "status": 404,
  "timestamp": "2025-11-01T10:00:00"
}
```

#### 2. 재고 부족
```json
{
  "message": "상품(ID: 1)의 재고가 부족합니다. (요청: 10, 재고: 5)",
  "status": 400,
  "timestamp": "2025-11-01T10:00:00"
}
```

#### 3. 유효성 검증 실패
```json
{
  "name": "카테고리 이름은 필수입니다.",
  "price": "가격은 0 이상이어야 합니다."
}
```

#### 4. 잘못된 주문 상태
```json
{
  "message": "PENDING 상태의 주문만 취소할 수 있습니다.",
  "status": 400,
  "timestamp": "2025-11-01T10:00:00"
}
```

---

## 실행 방법

### 1. 데이터베이스 준비
```bash
# MySQL 실행 (Docker)
docker run -d \
  --name mysql \
  -p 3307:3306 \
  -e MYSQL_ROOT_PASSWORD=root \
  -e MYSQL_DATABASE=spring_db \
  mysql:8.0
```

### 2. 애플리케이션 실행
```bash
# Gradle로 빌드
./gradlew clean build

# 애플리케이션 실행
./gradlew bootRun
```

### 3. API 테스트
- Swagger UI: http://localhost:8080/swagger-ui.html
- Health Check: http://localhost:8080/actuator/health

---

## 비즈니스 로직 요약

### 주문 생성 플로우
1. 주문 요청 접수
2. 상품 존재 여부 확인
3. **재고 확인 및 차감**
4. 주문 및 주문 상품 저장
5. 주문 정보 반환

### 주문 취소 플로우
1. 주문 조회
2. **주문 상태 확인 (PENDING만 가능)**
3. 상태를 CANCELED로 변경
4. **재고 복원**

### 환불 승인 플로우
1. 환불 요청 조회
2. **환불 상태 확인 (PENDING만 가능)**
3. 상태를 APPROVED로 변경
4. **재고 복원**

---

## 프로젝트 구조

```
src/main/java/com/sparta/demo/
├── controller/          # REST API 컨트롤러
│   ├── CategoryController.java
│   ├── ProductController.java
│   ├── OrderController.java
│   └── RefundController.java
├── service/            # 비즈니스 로직
│   ├── CategoryService.java
│   ├── ProductService.java
│   ├── OrderService.java
│   └── RefundService.java
├── repository/         # 데이터 접근 계층
│   ├── CategoryRepository.java
│   ├── ProductRepository.java
│   ├── OrderRepository.java
│   └── RefundRepository.java
├── domain/            # 엔티티 (도메인 모델)
│   ├── category/
│   ├── product/
│   ├── order/
│   └── refund/
├── dto/               # 요청/응답 DTO
│   ├── category/
│   ├── product/
│   ├── order/
│   └── refund/
└── exception/         # 예외 처리
    ├── GlobalExceptionHandler.java
    ├── ResourceNotFoundException.java
    ├── InsufficientStockException.java
    └── ...
```
