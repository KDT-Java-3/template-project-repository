# 재직자_JAVA_프로젝트_1주차 (week-01-project)

## 프로젝트 요구 사항

### 프로젝트 소개

👉 이번 주차는 커머스 시스템의 핵심적인 기능인 **상품 관리**와 **주문 처리**를 구현하는 것을 목표로 합니다.

실제 커머스 서비스에서 활용될 수 있는 구조를 이해하고, 효율적인 API 설계와 비즈니스 로직 구현을 통해 실무적인 개발 능력을 향상시키는 데 초점을 맞춥니다. 프로젝트 진행을
통해 **상품**, **카테고리**, **주문**, **환불**과 같은 주요 도메인을 다루며, **RESTful API 설계**를 실습합니다.

이 프로젝트는 크게 세 가지 주요 목표를 가지고 있습니다.

- RESTful 원칙을 기반으로 클라이언트와 서버 간의 데이터 통신 방식을 이해하고 효율적이며 유지보수가 용이한 API를 설계합니다.
- 상품과 주문 데이터를 관리하기 위한 도메인 로직을 설계하고, 주문 생성, 상태 관리, 결제 처리 같은 실제 비즈니스 시나리오를 구현합니다.
- 데이터베이스 설계를 통해 상품, 카테고리, 주문, 환불 등 주요 엔티티를 정의하고 관계형 데이터 모델링을 학습합니다.

---

## 구현 요구사항

### 1. **상품 관리 (Product Management)**

- **상품 등록 API**
    - 상품 이름, 설명, 가격, 재고, 카테고리 정보를 포함하여 새로운 상품을 등록할 수 있는 API를 구현합니다.
    - 필수 입력 필드: `name`, `price`, `stock`, `category_id`.
- **상품 조회 API**
    - 단일 상품 상세 조회 및 전체 상품 리스트를 조회하는 API를 구현합니다.
    - 검색 및 필터링 조건: 카테고리, 가격 범위, 상품명 키워드.
- **상품 수정 API**
    - 기존에 등록된 상품 정보를 수정할 수 있는 API를 구현합니다.
    - 변경 가능한 필드: `name`, `description`, `price`, `stock`, `category_id`.

### 2. **카테고리 관리 (Category Management)**

- **카테고리 등록 API**
    - 카테고리 이름과 설명을 입력받아 새로운 카테고리를 생성할 수 있는 API를 구현합니다.
- **카테고리 조회 API**
    - 모든 카테고리를 조회할 수 있는 API를 구현합니다.
    - 상품과 연관된 카테고리 정보를 포함하여 반환.
- **카테고리 수정 API**
    - 기존 카테고리 정보를 수정할 수 있는 API를 구현합니다.
    - 변경 가능한 필드: `name`, `description`.

### 3. **주문 관리 (Order Management)**

- **주문 생성 API**
    - 사용자가 장바구니에 담은 상품을 주문할 수 있는 API를 구현합니다.
    - 필수 입력 필드: `user_id`, `product_id`, `quantity`, `shipping_address`.
    - 주문 생성 시, 상품 재고를 확인하고 감소 처리.
- **주문 조회 API**
    - 특정 사용자의 주문 목록을 조회할 수 있는 API를 구현합니다.
    - 조회 가능한 정보: 주문 상태(`pending`, `completed`, `canceled`), 주문 날짜, 상품 상세.
- **주문 상태 변경 API**
    - 주문의 상태를 업데이트할 수 있는 API를 구현합니다.
    - 상태 변경 가능 범위: `pending` → `completed` / `canceled`.
- **주문 취소 API**
    - 사용자가 주문을 취소할 수 있는 API를 구현합니다.
    - 취소 조건: `pending` 상태의 주문만 취소 가능.

### 4. **환불 관리 (Refund Management)**

- **환불 요청 API**
    - 사용자가 특정 주문에 대해 환불 요청을 할 수 있는 API를 구현합니다.
    - 필수 입력 필드: `user_id`, `order_id`, `reason`.
- **환불 처리 API**
    - 관리자가 환불 요청을 승인하거나 거절할 수 있는 API를 구현합니다.
    - 환불 승인 시, 환불된 상품의 재고 복원.
- **환불 조회 API**
    - 특정 사용자의 환불 요청 목록을 조회할 수 있는 API를 구현합니다.
    - 조회 가능한 정보: 환불 상태(`pending`, `approved`, `rejected`), 환불 요청 날짜, 사유.

---

## 제출 방법 및 평가 기준

### 📅 **제출 방법**

**Branch 생성 및 작업**

제공된 주차별 Git repository에서 **신규 Branch**를 생성한 뒤 작업합니다.

- Branch 이름 형식:
    - `“work/{휴대전화번호}-{영문 이름}”`예: `work/1234-5678-john-doe`
- **Commit 및 Push**

  작업 내용을 **작업용 브랜치**에 Commit하고 Push합니다.

- **PR 요청**

  작업이 완료되면 **작업용 브랜치**에서 **제출용 브랜치**로 PR(Pull Request)을 생성합니다.

    - 제출용 브랜치 이름 형식:
        - `“project/{휴대전화번호}-{영문 이름}”`예: `project/1234-5678-john-doe`
- **PR 리뷰 및 병합**

  리뷰가 완료되면 **제출용 브랜치**에 PR을 병합합니다.

### 📖 **평가 방법**

**요구사항 달성 여부**와 **기능 확장을 고려한 코드 작성 여부**를 중점으로 진행됩니다.

---

## 데이터 모델링

### ERD

![erd](/src/main/resources/static/image/sparta-week1.png)

```
Table users {
  id              bigint [pk, increment]
  username        varchar(50) [not null]
  email           varchar(255) [not null, unique]
  password_hash   varchar(255) [not null]
  created_at      datetime [not null, default: `CURRENT_TIMESTAMP`]
  updated_at      datetime [not null, default: `CURRENT_TIMESTAMP`]

  Indexes {
    (email) [unique, name: 'uq_users_email']
  }
}

Table categories {
  id              bigint [pk, increment]
  name            varchar(100) [not null, unique]
  description     text
  created_at      datetime [not null, default: `CURRENT_TIMESTAMP`]
  updated_at      datetime [not null, default: `CURRENT_TIMESTAMP`]

  Indexes {
    (name) [unique, name: 'uq_categories_name']
  }
}

Table products {
  id              bigint [pk, increment]
  name            varchar(150) [not null]
  description     text
  price           decimal(10,2) [not null]
  stock           int [not null, default: 0]
  category_id     bigint [not null]
  created_at      datetime [not null, default: `CURRENT_TIMESTAMP`]
  updated_at      datetime [not null, default: `CURRENT_TIMESTAMP`]

  Indexes {
    (category_id) [name: 'idx_products_category_id']
    (name)        [name: 'idx_products_name']
    (price)       [name: 'idx_products_price']
  }
}

Table orders {
  id                bigint [pk, increment]
  user_id           bigint [not null]
  status            varchar(20) [not null, note: 'pending|completed|canceled']
  order_date        datetime [not null, default: `CURRENT_TIMESTAMP`]
  shipping_address  text [not null]
  created_at        datetime [not null, default: `CURRENT_TIMESTAMP`]
  updated_at        datetime [not null, default: `CURRENT_TIMESTAMP`]

  Indexes {
    (user_id)    [name: 'idx_orders_user_id']
    (status)     [name: 'idx_orders_status']
    (order_date) [name: 'idx_orders_order_date']
  }
}

Table order_items {
  id              bigint [pk, increment]
  order_id        bigint [not null]
  product_id      bigint [not null]
  quantity        int [not null]
  unit_price      decimal(10,2) [not null, note: 'price at ordering time']
  created_at      datetime [not null, default: `CURRENT_TIMESTAMP`]
  updated_at      datetime [not null, default: `CURRENT_TIMESTAMP`]

  Indexes {
    (order_id)              [name: 'idx_order_items_order_id']
    (product_id)            [name: 'idx_order_items_product_id']
    (order_id, product_id)  [unique, name: 'uq_order_items_order_product']
  }
}

Table refunds {
  id              bigint [pk, increment]
  user_id         bigint [not null]
  order_id        bigint [not null]
  status          varchar(20) [not null, note: 'pending|approved|rejected']
  reason          text [not null]
  requested_at    datetime [not null, default: `CURRENT_TIMESTAMP`]
  processed_at    datetime
  created_at      datetime [not null, default: `CURRENT_TIMESTAMP`]
  updated_at      datetime [not null, default: `CURRENT_TIMESTAMP`]

  Indexes {
    (user_id)      [name: 'idx_refunds_user_id']
    (order_id)     [name: 'idx_refunds_order_id']
    (status)       [name: 'idx_refunds_status']
    (requested_at) [name: 'idx_refunds_requested_at']
  }
}

Table roles {
  id          bigint [pk, increment]
  name        varchar(100) [not null, unique, note: 'e.g., ROLE_USER, ROLE_ADMIN']
  description text
  created_at  datetime [not null, default: `CURRENT_TIMESTAMP`]
  updated_at  datetime [not null, default: `CURRENT_TIMESTAMP`]

  Indexes {
    (name) [unique, name: 'uq_roles_name']
  }
}

Table user_roles {
  id          bigint [pk, increment] // 단일 PK (합성 PK 대신)
  user_id     bigint [not null]
  role_id     bigint [not null]
  granted_at  datetime [not null, default: `CURRENT_TIMESTAMP`]
  granted_by  bigint // 역할 부여 관리자 (선택)

  Indexes {
    (user_id, role_id) [unique, name: 'uq_user_roles_user_role']
    (user_id)          [name: 'idx_user_roles_user_id']
    (role_id)          [name: 'idx_user_roles_role_id']
  }
}

/* Relationships */
Ref: products.category_id > categories.id

Ref: orders.user_id > users.id
Ref: order_items.order_id > orders.id
Ref: order_items.product_id > products.id

Ref: refunds.user_id > users.id
Ref: refunds.order_id > orders.id

Ref: user_roles.user_id > users.id
Ref: user_roles.role_id > roles.id
Ref: user_roles.granted_by > users.id
```

---

## 공통 응답 처리 및 예외 핸들링

### 개요

모든 API의 응답 형식을 통일하고, 예외를 중앙에서 처리하여 일관성 있고 예측 가능한 API를 제공합니다.

### API 응답 구조

모든 API는 `ApiResponse<T>` 제네릭 클래스를 통해 통일된 형식으로 응답합니다.

#### 성공 응답

```json
{
  "result": true,
  "error": null,
  "message": {
    "id": 1,
    "name": "전자기기",
    "description": "전자제품 카테고리"
  }
}
```

#### 실패 응답

```json
{
  "result": false,
  "error": {
    "errorCode": "NOT_FOUND_CATEGORY",
    "errorMessage": "카테고리를 찾을 수 없습니다."
  },
  "message": null
}
```

### 구현 구조

#### 1. ApiResponse 클래스 (`global/dto/ApiResponse.java`)

```java
@Getter
@Builder
public class ApiResponse<T> {
    Boolean result;      // 성공/실패 여부
    Error error;         // 에러 정보 (실패 시)
    T message;           // 응답 데이터 (성공 시)

    // 정적 팩토리 메서드
    public static <T> ApiResponse<T> success(T message);
    public static <T> ResponseEntity<ApiResponse<T>> error(String code, String errorMessage);
    public static <T> ResponseEntity<ApiResponse<T>> badRequest(String code, String errorMessage);
    public static <T> ResponseEntity<ApiResponse<T>> serverError(String code, String errorMessage);
}
```

#### 2. ServiceException 클래스 (`global/exception/ServiceException.java`)

비즈니스 로직에서 발생하는 커스텀 예외입니다.

```java
public class ServiceException extends RuntimeException {
    String code;
    String message;

    public ServiceException(ServiceExceptionCode response) {
        super(response.getMessage());
        this.code = response.name();
        this.message = super.getMessage();
    }
}
```

#### 3. ServiceExceptionCode Enum (`global/exception/ServiceExceptionCode.java`)

애플리케이션에서 발생할 수 있는 모든 비즈니스 예외 코드를 정의합니다.

```java
public enum ServiceExceptionCode {
    // Category
    NOT_FOUND_CATEGORY("카테고리를 찾을 수 없습니다."),

    // Product
    NOT_FOUND_PRODUCT("상품을 찾을 수 없습니다."),
    INSUFFICIENT_STOCK("상품의 재고가 부족합니다."),

    // Order
    NOT_FOUND_ORDER("주문을 찾을 수 없습니다."),
    INVALID_ORDER_STATUS("유효하지 않은 주문 상태입니다."),
    CANNOT_CANCEL_ORDER("취소할 수 없는 주문입니다."),

    // Refund
    NOT_FOUND_REFUND("환불 요청을 찾을 수 없습니다."),
    INVALID_REFUND_STATUS("유효하지 않은 환불 상태입니다."),

    // User
    NOT_FOUND_USER("사용자를 찾을 수 없습니다.");

    final String message;
}
```

#### 4. GlobalExceptionHandler (`global/exception/GlobalExceptionHandler.java`)

`@RestControllerAdvice`를 사용하여 모든 예외를 중앙에서 처리합니다.

```java
@Hidden
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 비즈니스 예외 처리
    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<?> handleResponseException(ServiceException ex) {
        return ApiResponse.error(ex.getCode(), ex.getMessage());
    }

    // 유효성 검증 실패 처리
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> methodArgumentNotValidException(MethodArgumentNotValidException ex) {
        // 검증 오류 메시지 수집
        return ApiResponse.badRequest(VALIDATE_ERROR, errors);
    }

    // 기타 모든 예외 처리
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> serverException(Exception ex) {
        return ApiResponse.serverError(SERVER_ERROR, ex.getMessage());
    }
}
```

### 사용 예시

#### Controller에서 ApiResponse 사용

```java
@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ApiResponse<CategoryResponse> createCategory(
        @Valid @RequestBody CategoryCreateRequest request) {
        CategoryResponse response = categoryService.createCategory(request);
        return ApiResponse.success(response);  // 성공 응답
    }

    @GetMapping("/{id}")
    public ApiResponse<CategoryResponse> getCategoryById(@PathVariable Long id) {
        CategoryResponse response = categoryService.getCategoryById(id);
        return ApiResponse.success(response);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ApiResponse.success();  // 데이터 없는 성공 응답
    }
}
```

#### Service에서 예외 발생

```java
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryResponse getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
            .orElseThrow(() -> new ServiceException(ServiceExceptionCode.NOT_FOUND_CATEGORY));
        return categoryMapper.toResponse(category);
    }

    @Transactional
    public void deleteCategory(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new ServiceException(ServiceExceptionCode.NOT_FOUND_CATEGORY);
        }
        categoryRepository.deleteById(id);
    }
}
```

### 장점

1. **일관성**: 모든 API가 동일한 응답 구조를 가져 클라이언트 개발이 용이합니다.
2. **예측 가능성**: 에러 코드와 메시지가 명확하게 정의되어 있어 디버깅이 쉽습니다.
3. **유지보수성**: 예외 처리 로직이 중앙 집중화되어 코드 중복이 없습니다.
4. **확장성**: 새로운 예외 타입 추가가 간단합니다 (ServiceExceptionCode에 추가).

---

## 자료

### MapStruct 사용 예시

```java
// TODO: UserEntity, UserDto 클래스 생성 이후 실습 예정

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring") // MapStruct가 생성할 구현체를 스프링 Bean으로 등록
public interface UserMapper {

    // 필드명이 다를 경우 @Mapping으로 직접 지정
    @Mapping(source = "email", target = "emailAddress")
    UserDto toDto(UserEntity entity);

    // 필드명이 같다면 별도 매핑 없이 자동으로 변환
    UserEntity toEntity(UserDto dto);
}
```
