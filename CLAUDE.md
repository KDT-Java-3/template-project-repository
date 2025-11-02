# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## 프로젝트 개요

Java 17과 Gradle을 사용하는 Spring Boot 3.5.7 웹 애플리케이션입니다. MySQL 데이터베이스, JPA/Hibernate, Flyway 마이그레이션, Swagger API 문서화를 포함한 풀스택 RESTful API 서버입니다.

## 빌드 및 실행 명령어

### 프로젝트 빌드
```bash
./gradlew build
```

### 애플리케이션 실행
```bash
./gradlew bootRun
```

### 테스트 실행
```bash
./gradlew test
```

### 단일 테스트 클래스 실행
```bash
./gradlew test --tests DemoApplicationTests
```

### 특정 테스트 메서드 실행
```bash
./gradlew test --tests "DemoApplicationTests.contextLoads"
```

### 빌드 산출물 정리
```bash
./gradlew clean
```

### 의존성 확인
```bash
./gradlew dependencies
```

## 아키텍처

### 애플리케이션 구조
- **메인 애플리케이션**: `src/main/java/com/example/demo/DemoApplication.java` - `@SpringBootApplication` 어노테이션이 적용된 진입점
- **패키지**: `com.example.demo` - 애플리케이션의 기본 패키지
- **설정 파일**: `application.yml`이 활성 설정 파일 (포트 8080, MySQL 연결, JPA, Flyway, 로깅 설정 포함)
- **데이터베이스 마이그레이션**: `src/main/resources/db/migration/` - Flyway SQL 스크립트 위치

### 웹 서버 설정
- **Undertow** 사용 (Tomcat 대신) - 경량화 및 성능 최적화를 위해 `build.gradle`에서 Tomcat을 제외하고 Undertow를 포함
- 기본 포트: 8080

### 데이터베이스 설정
- **MySQL 8** 사용 (localhost:3306/spring_db)
- **Hibernate DDL Auto**: `none` - 스키마 관리는 Flyway로 처리
- **Flyway 마이그레이션**: 활성화됨, 마이그레이션 파일은 `classpath:db/migration` (= `src/main/resources/db/migration/`)
- SQL 쿼리는 DEBUG 레벨로 로그에 출력됨 (`org.hibernate.SQL: DEBUG`)
- **스키마 문서**: `docs/database-schema.md` - 모든 테이블의 JPA 엔티티 매핑 정보 및 상세 설명

### 주요 의존성
- `spring-boot-starter-web` (Tomcat 제외) + `spring-boot-starter-undertow`: REST API, Spring MVC, JSON 처리
- `spring-boot-starter-data-jpa`: JPA/Hibernate ORM
- `spring-boot-starter-validation`: Bean Validation (JSR-380)
- `spring-boot-starter-actuator`: 애플리케이션 모니터링 및 헬스체크 엔드포인트
- `springdoc-openapi-starter-webmvc-ui` (2.0.2): Swagger/OpenAPI 3 문서 자동 생성 및 UI
- `flyway-core` + `flyway-mysql`: 데이터베이스 스키마 버전 관리 및 마이그레이션
- `mysql-connector-j`: MySQL JDBC 드라이버
- `lombok`: 보일러플레이트 코드 감소 (Getter/Setter, Constructor 등)
- `mapstruct` (1.5.5.Final): DTO-Entity 매핑 자동화
- `spring-boot-devtools`: 개발 시 자동 재시작 및 LiveReload

### Annotation Processors
`build.gradle`의 `annotationProcessor` 설정:
- `lombok`: 컴파일 타임에 Lombok 어노테이션 처리
- `mapstruct-processor`: 컴파일 타임에 MapStruct 매퍼 구현체 자동 생성

## 개발 가이드

### 새로운 컨트롤러/서비스 추가
- 컨트롤러는 `com.example.demo` 패키지 또는 하위 패키지에 배치
- Spring Boot의 컴포넌트 스캔이 기본 패키지 내의 `@Controller`, `@RestController`, `@Service`, `@Repository` 어노테이션을 자동으로 감지함
- Swagger UI는 자동으로 모든 REST 엔드포인트를 스캔하여 문서화함 (일반적으로 `/swagger-ui.html` 또는 `/swagger-ui/index.html`에서 접근 가능)

### 데이터베이스 마이그레이션 (Flyway)
- 새로운 마이그레이션 파일은 `src/main/resources/db/migration/` 디렉토리에 생성
- 파일 명명 규칙: `V{version}__{description}.sql` (예: `V1__init_schema.sql`, `V2__add_users_table.sql`)
- 애플리케이션 시작 시 Flyway가 자동으로 미적용 마이그레이션을 실행
- `spring.jpa.hibernate.ddl-auto=none`이므로 모든 스키마 변경은 Flyway를 통해 관리
- **중요**: 이미 적용된 마이그레이션 파일(V1, V2 등)은 절대 수정하지 말 것. 변경사항은 항상 새로운 버전(V3, V4...)으로 추가
- 테이블 스키마와 JPA 엔티티 매핑 정보는 `docs/database-schema.md` 참조

### 엔티티 및 DTO 매핑
- JPA 엔티티는 `@Entity` 어노테이션 사용
- Lombok으로 보일러플레이트 감소: `@Getter`, `@Setter`, `@NoArgsConstructor`, `@AllArgsConstructor`, `@Builder` 등
- MapStruct로 DTO-Entity 매핑: `@Mapper` 인터페이스 정의 후 컴파일 시 구현체 자동 생성

### 테스트
- 테스트 클래스는 `src/test/java/com/example/demo/`에 배치
- 통합 테스트에는 `@SpringBootTest` 사용
- 웹 계층만 테스트하려면 `@WebMvcTest` 사용
- JUnit 5 (Jupiter) 기반, Mockito로 목 객체 생성

### API 문서화
- Springdoc이 자동으로 OpenAPI 3.0 스펙 생성
- 컨트롤러에 `@Operation`, `@ApiResponse`, `@Tag` 등의 어노테이션 추가하여 문서 상세화 가능