# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

Spring Boot 3.5.7 REST API application using Java 17, JPA, MySQL, and Flyway for database migrations. The application uses Undertow as the embedded server (not Tomcat), Lombok for boilerplate reduction, and MapStruct for DTO mapping.

## Build and Run Commands

### Build the project
```bash
./gradlew build
```

### Run the application
```bash
./gradlew bootRun
```

### Run tests
```bash
./gradlew test
```

### Run a single test class
```bash
./gradlew test --tests "com.example.demo.DemoApplicationTests"
```

### Clean build artifacts
```bash
./gradlew clean
```

## Database Setup

The application requires MySQL running locally:
- Host: localhost:3306
- Database: spring_db
- Username: root
- Password: root

Database schema is managed by Flyway migrations in `src/main/resources/db/migration/`. Migration files follow the naming pattern `V{version}__{description}.sql`.

## Application Architecture

### Layer Structure
The application follows a standard Spring Boot layered architecture:

- **Controller Layer** (`com.example.demo.controller`): REST endpoints
- **Service Layer** (`com.example.demo.service`): Business logic
- **Repository Layer** (`com.example.demo.repository`): Data access using Spring Data JPA
- **Entity Layer** (`com.example.demo.entity`): JPA entities mapped to database tables
- **DTO Layer** (`com.example.demo.controller.dto`): Request/Response objects

### API Response Structure
All API responses use a standardized wrapper (`com.example.demo.common.ApiResponse`) with the following structure:
- `result`: Boolean indicating success/failure
- `data`: Response payload (generic type T)
- `error`: Error details (code and message) when result is false

Success responses use `ApiResponse.success()` or `ApiResponse.success(data)`.
Error responses use `ApiResponse.error(code, message)` which returns a ResponseEntity with bad request status.

### Key Technologies and Patterns

**Annotation Processing**: The project uses both Lombok and MapStruct annotation processors. When adding new entities or DTOs, ensure proper annotations are used (@Entity, @Getter, @Builder, etc.).

**Database Migration**: Never modify existing Flyway migration files. Create new migration files with incremented version numbers (V2__, V3__, etc.). The application is configured with `ddl-auto: none`, so all schema changes must be done through Flyway.

**Hibernate Configuration**: SQL queries are logged at DEBUG level. The application uses MySQL8Dialect for database-specific optimizations.

## API Documentation

Swagger UI is available at http://localhost:8080/swagger-ui.html when the application is running.

## Development

**DevTools**: Spring Boot DevTools is included for automatic restarts during development.

**Actuator**: Spring Boot Actuator endpoints are available for monitoring and management.

**Server**: The application uses Undertow instead of Tomcat. Tomcat is explicitly excluded from `spring-boot-starter-web`.
