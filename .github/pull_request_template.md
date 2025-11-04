### 🚩제목 : [1주차] JAVA_01_커머스: 상품/주문_신지섭_프로젝트

----

### 📝작업 내용 (필수) : 이번 PR에서 작업한 내용을 설명해주세요

1. **프로젝트 기본 설정**
    - BaseEntity 구현 (JPA Auditing을 통한 created_at/updated_at 자동 관리)
    - JpaAuditingConfig 설정 (@EnableJpaAuditing)
    - SwaggerConfig 설정 (API 문서화)
    - 도메인 중심 패키지 구조 설계 (domain/{domain_name}/)

2. **User 도메인 구현**
    - User 엔티티 생성 (username, password, email)
    - BaseEntity 상속을 통한 타임스탬프 자동 관리

3. **Category 도메인 CRUD 구현**
    - POST /api/categories - 카테고리 등록 API
    - GET /api/categories - 전체 카테고리 조회 API
    - GET /api/categories/{id} - 특정 카테고리 조회 API
    - PUT /api/categories/{id} - 카테고리 수정 API
    - DELETE /api/categories/{id} - 카테고리 삭제 API
    - CategoryCreateRequest/CategoryUpdateRequest DTO 구현
    - CategoryResponse DTO 구현
    - CategoryMapper (MapStruct) 구현
    - 트랜잭션 관리 및 예외 처리 (@Transactional)
    - Swagger API 문서화

4. **문서화**
    - README.md에 ERD 스키마 정의 추가
    - CLAUDE.md 프로젝트 가이드 작성

----

### 🔒고민이 되었던 부분과 어떻게 대응하셨는지 남겨주세요 (선택)

- MapStruct를 사용한 Entity-DTO 변환 시 자동 매핑이 제대로 작동하는지 확인이 필요했습니다. 빌드 후 생성된 MapperImpl 클래스를 확인하여 정상 동작을
  검증했습니다.
- JPA Auditing 설정 시 @EnableJpaAuditing 어노테이션 위치를 별도의 Config 클래스로 분리하여 관심사를 명확히 했습니다.

----

### 💬리뷰 요구사항(선택) : 리뷰어가 특별히 봐주었으면 하는 부분이 있다면 작성해주세요

- 