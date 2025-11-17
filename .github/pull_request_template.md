### 🚩제목 : [3주차] JAVA_01_커머스: 상품/주문_신지섭_프로젝트

----

### 📝작업 내용 (필수) : 이번 PR에서 작업한 내용을 설명해주세요

1. **상품 관리 API 구현**
    - Product Entity 및 CRUD API 구현
    - QueryDSL 기반 동적 검색 (카테고리, 가격 범위, 상품명) 및 페이징
    - 다중 정렬 조건 지원 (price, createdAt, stock, name)
    - 주문 완료된 상품 삭제 제한 로직

2. **Category 계층형 구조 및 삭제 검증**
    - self-join을 통한 계층형 카테고리 구조 구현
    - 하위 카테고리 및 연관 상품 존재 시 삭제 제한
    - CategoryQueryRepository 구현 (QueryDSL)

3. **주문 관리 API 구현**
    - Order/OrderItem 엔티티 및 양방향 관계 설정
    - QueryDSL 기반 주문 검색 및 집계 (OrderQueryRepository)
    - 비관적 락을 통한 재고 동시성 제어
    - 주문 생성/조회/취소/완료 API 구현
    - 주문 취소 시 재고 복구 처리

4. **버그 수정**
    - OrderQueryRepository sum() 메서드 타입 변환 오류 수정

----

### 🔒고민이 되었던 부분과 어떻게 대응하셨는지 남겨주세요 (선택)

- **QueryDSL 집계 함수**: OrderQueryRepository에서 `sum()` 메서드 사용 시 타입 변환 오류가 발생했습니다.
  `Expressions.numberTemplate()`을 사용하여 SQL 집계를 직접 처리하고 COALESCE로 NULL 안전 처리를 추가했습니다.

----

### 💬리뷰 요구사항(선택) : 리뷰어가 특별히 봐주었으면 하는 부분이 있다면 작성해주세요

- 