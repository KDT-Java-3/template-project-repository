# API 흐름 정리

각 도메인별 API의 전체 흐름을 단계별로 정리한 문서입니다.

---

## 1. User 도메인

### 1-1. 사용자 생성 API (POST /api/users)

| 단계 | 위치 | 사용 DTO | 사용 Repository | Repository 메서드 | 필요한 값 |
|------|------|----------|----------------|------------------|-----------|
| 1. 요청 받기 | Controller | `UserDto.Request` | - | - | `username`, `email`, `password` |
| 2. username 중복 체크 | Service | - | `UserRepository` | `existsByUsername()` | `username` |
| 3. email 중복 체크 | Service | - | `UserRepository` | `existsByEmail()` | `email` |
| 4. User 엔티티 생성 | Service | - | - | - | `username`, `email`, `password` |
| 5. User 저장 | Service | - | `UserRepository` | `save()` | `user` 객체 |
| 6. Response 변환 | Service | `UserDto.Response` | - | - | `savedUser` (User 객체) |
| 7. 응답 반환 | Controller | `UserDto.Response` | - | - | - |

**Request JSON 예시:**
```json
POST /api/users
{
  "username": "hong",
  "email": "hong@example.com",
  "password": "password123"
}
```

**Response JSON 예시:**
```json
{
  "id": 1,
  "username": "hong",
  "email": "hong@example.com",
  "createdAt": "2025-11-02T10:30:00"
}
```

---

### 1-2. 사용자 조회 API (GET /api/users/{id})

| 단계 | 위치 | 사용 DTO | 사용 Repository | Repository 메서드 | 필요한 값 |
|------|------|----------|----------------|------------------|-----------|
| 1. 요청 받기 | Controller | - | - | - | `id` (PathVariable) |
| 2. User 조회 | Service | - | `UserRepository` | `findById()` | `id` |
| 3. Response 변환 | Service | `UserDto.Response` | - | - | `user` (User 객체) |
| 4. 응답 반환 | Controller | `UserDto.Response` | - | - | - |

---

### 1-3. 전체 사용자 목록 조회 API (GET /api/users)

| 단계 | 위치 | 사용 DTO | 사용 Repository | Repository 메서드 | 필요한 값 |
|------|------|----------|----------------|------------------|-----------|
| 1. 요청 받기 | Controller | - | - | - | - |
| 2. 전체 User 조회 | Service | - | `UserRepository` | `findAll()` | - |
| 3. Response 변환 (List) | Service | `List<UserDto.Response>` | - | - | `users` (List<User>) |
| 4. 응답 반환 | Controller | `List<UserDto.Response>` | - | - | - |

---

## 2. Category 도메인

### 2-1. 카테고리 생성 API (POST /api/categories)

| 단계 | 위치 | 사용 DTO | 사용 Repository | Repository 메서드 | 필요한 값 |
|------|------|----------|----------------|------------------|-----------|
| 1. 요청 받기 | Controller | `CategoryDto.Request` | - | - | `name`, `description`, `parentId` |
| 2. name 중복 체크 | Service | - | `CategoryRepository` | `existsByName()` | `name` |
| 3. Parent Category 조회 | Service | - | `CategoryRepository` | `findById()` | `parentId` (null 가능) |
| 4. Category 엔티티 생성 | Service | - | - | - | `name`, `description`, `parent` |
| 5. Category 저장 | Service | - | `CategoryRepository` | `save()` | `category` 객체 |
| 6. Response 변환 | Service | `CategoryDto.Response` | - | - | `savedCategory` |
| 7. 응답 반환 | Controller | `CategoryDto.Response` | - | - | - |

**Request JSON 예시:**
```json
POST /api/categories
{
  "name": "스마트폰",
  "description": "스마트폰 카테고리",
  "parentId": 1
}
```

**Response JSON 예시:**
```json
{
  "id": 2,
  "name": "스마트폰",
  "description": "스마트폰 카테고리",
  "parentId": 1,
  "parentName": "전자제품",
  "createdAt": "2025-11-02T10:30:00"
}
```

**특이사항:**
- `parentId`는 선택 필드 (최상위 카테고리는 null)
- 부모 카테고리가 없으면 3단계 생략

---

## 3. Product 도메인

### 3-1. 상품 생성 API (POST /api/products)

| 단계 | 위치 | 사용 DTO | 사용 Repository | Repository 메서드 | 필요한 값 |
|------|------|----------|----------------|------------------|-----------|
| 1. 요청 받기 | Controller | `ProductDto.Request` | - | - | `categoryId`, `name`, `description`, `price`, `stockQuantity` |
| 2. name 중복 체크 | Service | - | `ProductRepository` | `existsByName()` | `name` |
| 3. Category 조회 | Service | - | `CategoryRepository` | `findById()` | `categoryId` |
| 4. Product 엔티티 생성 | Service | - | - | - | `category`, `name`, `description`, `price`, `stockQuantity` |
| 5. Product 저장 | Service | - | `ProductRepository` | `save()` | `product` 객체 |
| 6. Response 변환 | Service | `ProductDto.Response` | - | - | `savedProduct` |
| 7. 응답 반환 | Controller | `ProductDto.Response` | - | - | - |

**Request JSON 예시:**
```json
POST /api/products
{
  "categoryId": 2,
  "name": "갤럭시 S24",
  "description": "삼성 최신 스마트폰",
  "price": 1000000,
  "stockQuantity": 50
}
```

**Response JSON 예시:**
```json
{
  "id": 1,
  "categoryId": 2,
  "categoryName": "스마트폰",
  "name": "갤럭시 S24",
  "description": "삼성 최신 스마트폰",
  "price": 1000000,
  "stockQuantity": 50,
  "createdAt": "2025-11-02T10:30:00"
}
```

**특이사항:**
- `categoryId` 필수 (상품은 반드시 카테고리에 속함)
- Category 조회 실패 시 에러 발생

---

## 4. Purchase 도메인

### 4-1. 주문 생성 API (POST /api/purchases)

| 단계 | 위치 | 사용 DTO | 사용 Repository | Repository 메서드 | 필요한 값 |
|------|------|----------|----------------|------------------|-----------|
| 1. 요청 받기 | Controller | `PurchaseDto.Request` | - | - | `userId`, `shippingAddress`, `products` |
| 2. User 조회 | Service | - | `UserRepository` | `findById()` | `userId` |
| 3. Purchase 엔티티 생성 | Service | - | - | - | `user`, `shippingAddress`, `status="pending"` |
| 4-1. Product 조회 (반복) | Service | - | `ProductRepository` | `findById()` | `productId` (각 상품별) |
| 4-2. 재고 확인 (반복) | Service | - | - | - | `product.stockQuantity`, `quantity` |
| 4-3. 재고 감소 (반복) | Service | - | - | - | `product.stockQuantity - quantity` |
| 4-4. PurchaseProduct 생성 (반복) | Service | - | - | - | `purchase`, `product`, `quantity`, `price` |
| 4-5. 총액 계산 (반복) | Service | - | - | - | `price * quantity` |
| 5. Purchase 총액 설정 | Service | - | - | - | `totalPrice` |
| 6. Purchase 저장 | Service | - | `PurchaseRepository` | `save()` | `purchase` 객체 (cascade로 PurchaseProduct도 함께 저장) |
| 7. Response 변환 | Service | `PurchaseDto.Response` | - | - | `savedPurchase` |
| 8. 응답 반환 | Controller | `PurchaseDto.Response` | - | - | - |

**Request JSON 예시:**
```json
POST /api/purchases
{
  "userId": 1,
  "shippingAddress": "서울시 강남구 테헤란로 123",
  "products": [
    {
      "productId": 1,
      "quantity": 2
    },
    {
      "productId": 3,
      "quantity": 1
    }
  ]
}
```

**Response JSON 예시:**
```json
{
  "id": 1,
  "userId": 1,
  "totalPrice": 3000000,
  "status": "pending",
  "shippingAddress": "서울시 강남구 테헤란로 123",
  "createdAt": "2025-11-02T10:30:00"
}
```

**특이사항:**
- 여러 상품을 한 번에 주문 가능 (List<OrderItem>)
- 재고 확인 → 재고 감소 → PurchaseProduct 생성 → 총액 계산 (반복)
- 재고 부족 시 전체 주문 실패 (트랜잭션 롤백)
- `@Transactional` 필수!

---

### 4-2. 주문 조회 API (GET /api/purchases/{id})

| 단계 | 위치 | 사용 DTO | 사용 Repository | Repository 메서드 | 필요한 값 |
|------|------|----------|----------------|------------------|-----------|
| 1. 요청 받기 | Controller | - | - | - | `id` (PathVariable) |
| 2. Purchase 조회 | Service | - | `PurchaseRepository` | `findById()` | `id` |
| 3. Response 변환 | Service | `PurchaseDto.Response` | - | - | `purchase` (Lazy 로딩: user, purchaseProducts 포함) |
| 4. 응답 반환 | Controller | `PurchaseDto.Response` | - | - | - |

**특이사항:**
- Lazy 로딩 때문에 `@Transactional(readOnly = true)` 필요
- Response에 상품 목록 포함 가능 (필요시)

---

### 4-3. 사용자별 주문 목록 조회 API (GET /api/purchases?userId=1)

| 단계 | 위치 | 사용 DTO | 사용 Repository | Repository 메서드 | 필요한 값 |
|------|------|----------|----------------|------------------|-----------|
| 1. 요청 받기 | Controller | - | - | - | `userId` (RequestParam) |
| 2. User의 Purchase 목록 조회 | Service | - | `PurchaseRepository` | `findByUserId()` (커스텀) | `userId` |
| 3. Response 변환 (List) | Service | `List<PurchaseDto.Response>` | - | - | `purchases` |
| 4. 응답 반환 | Controller | `List<PurchaseDto.Response>` | - | - | - |

**필요한 Repository 메서드 추가:**
```java
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
    List<Purchase> findByUserIdId(Long userId);
}
```

---

### 4-4. 주문 상태 변경 API (PATCH /api/purchases/{id}/status)

| 단계 | 위치 | 사용 DTO | 사용 Repository | Repository 메서드 | 필요한 값 |
|------|------|----------|----------------|------------------|-----------|
| 1. 요청 받기 | Controller | `PurchaseDto.StatusUpdateRequest` | - | - | `id` (PathVariable), `status` (RequestBody) |
| 2. Purchase 조회 | Service | - | `PurchaseRepository` | `findById()` | `id` |
| 3. 상태 변경 | Service | - | - | - | `purchase.setStatus(status)` |
| 4. Purchase 저장 | Service | - | `PurchaseRepository` | `save()` | `purchase` 객체 |
| 5. Response 변환 | Service | `PurchaseDto.Response` | - | - | `savedPurchase` |
| 6. 응답 반환 | Controller | `PurchaseDto.Response` | - | - | - |

**Request JSON 예시:**
```json
PATCH /api/purchases/1/status
{
  "status": "completed"
}
```

---

### 4-5. 주문 취소 API (DELETE /api/purchases/{id})

| 단계 | 위치 | 사용 DTO | 사용 Repository | Repository 메서드 | 필요한 값 |
|------|------|----------|----------------|------------------|-----------|
| 1. 요청 받기 | Controller | - | - | - | `id` (PathVariable) |
| 2. Purchase 조회 | Service | - | `PurchaseRepository` | `findById()` | `id` |
| 3. 상태 확인 | Service | - | - | - | `purchase.getStatus()` (pending만 취소 가능) |
| 4. 상태 변경 | Service | - | - | - | `purchase.setStatus("canceled")` |
| 5-1. PurchaseProduct 조회 (반복) | Service | - | - | - | `purchase.getPurchaseProducts()` |
| 5-2. 재고 복구 (반복) | Service | - | - | - | `product.stockQuantity + quantity` |
| 6. Purchase 저장 | Service | - | `PurchaseRepository` | `save()` | `purchase` 객체 |
| 7. 응답 반환 | Controller | - | - | - | HTTP 204 No Content |

**특이사항:**
- `status`가 `pending`일 때만 취소 가능
- 재고 복구 필수
- `@Transactional` 필수!

---

## 공통 패턴

### DTO 사용 시점
- **Request DTO**: Controller → Service (클라이언트 입력)
- **Response DTO**: Service → Controller (서버 응답)

### Repository 메서드
- **기본 제공**: `save()`, `findById()`, `findAll()`, `delete()`
- **커스텀**: `existsByUsername()`, `existsByEmail()`, `findByUserId()` 등

### @Transactional
- **쓰기 작업**: `@Transactional` 필수
- **읽기 작업**: `@Transactional(readOnly = true)` 권장
- **복잡한 로직** (재고 감소 등): `@Transactional` 필수!

---

**작성일**: 2025-11-02
**프로젝트**: Spring Boot Demo Application