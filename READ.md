커머스 시스템 프로젝트

RESTful API 기반의 상품 · 카테고리 · 주문 · 환불 관리 시스템

📖 프로젝트 소개

이번 프로젝트는 커머스 시스템의 핵심 기능인 상품 관리(Product Management) 와 주문 처리(Order Management) 를 중심으로, 실제 전자상거래 서비스 구조를 이해하고 효율적인 API 설계 및 비즈니스 로직 구현 능력을 향상시키는 것을 목표로 합니다.

프로젝트를 통해 다음을 학습합니다:

RESTful 원칙 기반의 API 설계 및 클라이언트-서버 통신 구조 이해

상품, 카테고리, 주문, 환불 도메인의 관계형 데이터 모델링

주문 생성, 재고 관리, 상태 변경, 환불 처리 등 실무 시나리오 구현

⚙️ 구현 기능 요약
1️⃣ 상품 관리 (Product Management)

상품 등록: 이름, 설명, 가격, 재고, 카테고리로 상품 등록

상품 조회: 단일/전체 상품 조회 및 카테고리·가격·키워드 검색 지원

상품 수정: name, description, price, stock, category_id 변경 가능

2️⃣ 카테고리 관리 (Category Management)

카테고리 등록: 이름, 설명으로 신규 카테고리 생성

카테고리 조회: 전체 카테고리 및 관련 상품 조회

카테고리 수정: name, description 수정 가능

3️⃣ 주문 관리 (Order Management)

주문 생성: user_id, product_id, quantity, shipping_address 입력

주문 시 재고 확인 및 감소 처리

주문 조회: 특정 사용자 주문 목록 반환 (pending, completed, canceled)

주문 상태 변경: pending → completed / pending → canceled

주문 취소: pending 상태 주문만 취소 가능 (재고 복원)

4️⃣ 환불 관리 (Refund Management)

환불 요청: user_id, order_id, reason 입력

환불 처리: 관리자가 승인(approved) 또는 거절(rejected)

환불 조회: 사용자별 환불 내역 조회 (pending, approved, rejected)