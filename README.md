# 재직자_JAVA_프로젝트_1주차 (week-01-project)

## 프로젝트 요구 사항

### 프로젝트 소개


👉 이번 주차는 커머스 시스템의 핵심적인 기능인 **상품 관리**와 **주문 처리**를 구현하는 것을 목표로 합니다.

실제 커머스 서비스에서 활용될 수 있는 구조를 이해하고, 효율적인 API 설계와 비즈니스 로직 구현을 통해 실무적인 개발 능력을 향상시키는 데 초점을 맞춥니다. 프로젝트 진행을 통해 **상품**, **카테고리**, **주문**, **환불**과 같은 주요 도메인을 다루며, **RESTful API 설계**를 실습합니다.

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
