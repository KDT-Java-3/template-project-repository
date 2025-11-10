# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is a **Week 1 Commerce System** project for learning Spring Boot development, focusing on implementing core e-commerce features including **Product Management**, **Order Management**, **Category Management**, and **Refund Management**.

**Tech Stack:**
- Spring Boot 3.5.7
- Java 17
- MySQL (database: `commerce`)
- Undertow (web server, not Tomcat)
- JPA/Hibernate
- Flyway (currently disabled in favor of `ddl-auto: create`)
- MapStruct (for DTO mapping)
- Springdoc OpenAPI (Swagger UI)

## Build & Run Commands

### Build
```bash
./gradlew build
```

### Run Application
```bash
./gradlew bootRun
```

### Run Tests
```bash
./gradlew test
```

### Run Single Test Class
```bash
./gradlew test --tests "com.example.demo.ClassName"
```

### Clean Build
```bash
./gradlew clean build
```

## Database Setup

**Required Environment Variables:**
- `DB_USER`: MySQL username
- `DB_PASSWORD`: MySQL password

**Database Connection:**
- URL: `jdbc:mysql://localhost:3306/commerce`
- Schema: `commerce`
- Port: `3306`

**Important:** JPA is currently set to `ddl-auto: create` which will drop and recreate tables on every startup. Flyway is present but disabled (commented out in `application.yaml`).

## Architecture

### Package Structure (Domain-Driven)

```
com.example.demo/
├── common/               # Shared base classes
│   ├── BaseEntity       # Auditing fields (created_at, updated_at)
│   └── util/            # Utility classes
├── domain/              # Domain modules
│   ├── user/
│   ├── product/
│   ├── category/
│   ├── order/
│   └── refund/
└── global/              # Global configurations
    └── config/
        ├── JpaAuditingConfig
        └── SwaggerConfig
```

**Each domain follows this structure:**
```
domain/{domain_name}/
├── entity/         # JPA entities
├── dto/
│   ├── request/    # Request DTOs
│   └── response/   # Response DTOs
├── repository/     # Spring Data JPA repositories
├── service/        # Business logic
├── controller/     # REST controllers
└── mapper/         # MapStruct mappers (optional)
```

### Key Architectural Patterns

1. **BaseEntity Pattern**: All entities extend `BaseEntity` which provides automatic `created_at` and `updated_at` timestamp management via JPA Auditing.

2. **Builder Pattern**: Entities use Lombok `@Builder` for object creation.

3. **DTO Mapping**: MapStruct is configured for entity-DTO conversions. Use `@Mapper(componentModel = "spring")` to generate Spring beans.

4. **Entity Design**:
   - Use `@DynamicInsert` and `@DynamicUpdate` for optimized SQL generation
   - Protected no-args constructor: `@NoArgsConstructor(access = AccessLevel.PROTECTED)`
   - Column definitions should specify `nullable`, `length`, and `columnDefinition` where applicable

## Development Guidelines

### Entity Timestamps
- All entities must extend `BaseEntity` to get automatic `created_at`/`updated_at` fields
- Column definition: `datetime(3)` for millisecond precision
- `created_at` is `updatable = false`

### MapStruct Usage
```java
@Mapper(componentModel = "spring")
public interface YourMapper {
    @Mapping(source = "fieldA", target = "fieldB") // Only if field names differ
    YourDto toDto(YourEntity entity);

    YourEntity toEntity(YourDto dto);
}
```

### Database Migration Strategy
- Currently using `hibernate.ddl-auto: create` for development
- Flyway is available but commented out
- Migration files should be placed in `src/main/resources/db/migration/`
- Naming convention: `V{version}__{description}.sql` (e.g., `V1__init_table.sql`)

### API Documentation
- Swagger UI is enabled and accessible at: `http://localhost:8080/swagger-ui.html`
- Use Swagger annotations in controllers for better documentation

## Git Workflow

**Branch Naming:**
- Working branch: `work/{phone-number}-{name}` (e.g., `work/9809-8005-jisup-shin`)
- Submission branch: `project/{phone-number}-{name}`

**Workflow:**
1. Create/checkout work branch
2. Commit and push to work branch
3. Create PR from work branch → submission branch
4. Merge after review

**Commit Message Convention:**

Follow the Conventional Commits format defined in `COMMIT_CONVENTION.md`:

```bash
<type>[optional scope]: <description>

[optional body]
```

**Important for Claude Code:**
- DO NOT add "🤖 Generated with Claude Code" footer
- DO NOT add "Co-Authored-By: Claude" footer
- Keep commit messages clean and professional

**Common types:**
- `feat`: New feature
- `fix`: Bug fix
- `docs`: Documentation changes
- `style`: Code style changes (formatting, etc.)
- `refactor`: Code refactoring
- `test`: Adding or modifying tests
- `build`: Build system or dependency changes
- `chore`: Other changes (library updates, etc.)

**Examples:**
```bash
feat(auth): 로그인 토큰 만료 처리 추가
fix(api): 세션 만료 오류 해결
build: Querydsl 설정 및 보안 취약점 해결
docs: API 문서 업데이트
```

## Domain Requirements Reference

### Product Management
- Registration: name, price, stock, category_id
- Query: single/list, filtering by category/price/name
- Update: name, description, price, stock, category_id

### Category Management
- Registration: name, description
- Query: list all with related products
- Update: name, description

### Order Management
- Creation: user_id, product_id, quantity, shipping_address (with stock validation)
- Query: by user, includes status/date/details
- Status transitions: `pending` → `completed` / `canceled`
- Cancellation: only `pending` orders

### Refund Management
- Request: user_id, order_id, reason
- Processing: admin approval/rejection with stock restoration
- Query: by user, includes status/date/reason
- Status: `pending`, `approved`, `rejected`

## ERD Reference

ERD diagram is available at: `src/main/resources/static/image/sparta-week1.png`
