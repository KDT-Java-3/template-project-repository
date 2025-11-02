package com.example.demo.domain.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

/**
 * User 관련 DTO(Data Transfer Object) 클래스
 *
 * DTO란?
 * - 계층 간 데이터 전송을 위한 객체
 * - 클라이언트 ↔ 서버 간 데이터를 주고받을 때 사용
 * - 엔티티(User)를 직접 노출하지 않고 필요한 데이터만 전달
 *
 * 왜 엔티티 대신 DTO를 사용하나?
 * 1. 보안: password 같은 민감한 정보 숨김
 * 2. 유연성: API 응답 형식을 자유롭게 변경 가능
 * 3. 검증: 클라이언트 입력값 검증 (@NotBlank, @Email 등)
 * 4. 독립성: DB 스키마 변경이 API에 영향 안 줌
 */
public class UserDto {

    // ============================================
    // Request DTO: 클라이언트 → 서버 (입력 데이터)
    // ============================================
    /**
     * 사용자 생성/수정 요청 DTO
     *
     * 사용 시나리오:
     * - 회원가입 (POST /api/users)
     * - 회원정보 수정 (PUT /api/users/{id})
     *
     * 예시 JSON:
     * {
     *   "username": "john",
     *   "email": "john@example.com",
     *   "password": "password123"
     * }
     */
    @Getter  // 모든 필드의 getter 메서드 생성
    @Setter  // 모든 필드의 setter 메서드 생성 (JSON 역직렬화에 필요)
    @NoArgsConstructor  // 기본 생성자 (Jackson이 JSON을 객체로 변환할 때 필요)
    @AllArgsConstructor  // 모든 필드를 받는 생성자
    @Builder  // 빌더 패턴 (테스트 코드 작성 시 유용)
    public static class Request {
        // static class: UserDto 인스턴스 없이도 UserDto.Request로 접근 가능

        // ===== username 필드 =====
        /**
         * 사용자명
         *
         * 검증 규칙:
         * - @NotBlank: null, 빈 문자열(""), 공백(" ") 불가
         * - @Size: 3자 이상 50자 이하
         *
         * 검증 실패 시: "Username must be between 3 and 50 characters" 메시지 반환
         */
        @NotBlank(message = "Username is required")
        @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
        private String username;

        // ===== email 필드 =====
        /**
         * 이메일 주소
         *
         * 검증 규칙:
         * - @NotBlank: 필수 입력
         * - @Email: 이메일 형식 검증 (예: "user@example.com")
         * - @Size: 최대 100자
         *
         * 검증 실패 시: "Email should be valid" 메시지 반환
         */
        @NotBlank(message = "Email is required")
        @Email(message = "Email should be valid")
        @Size(max = 100, message = "Email must not exceed 100 characters")
        private String email;

        // ===== password 필드 =====
        /**
         * 비밀번호 (평문)
         *
         * 검증 규칙:
         * - @NotBlank: 필수 입력
         * - @Size: 최소 6자 이상
         *
         * 주의: 이 필드는 평문이므로, 서비스 계층에서 반드시 암호화해야 함 (BCrypt 등)
         * 검증 실패 시: "Password must be at least 6 characters" 메시지 반환
         */
        @NotBlank(message = "Password is required")
        @Size(min = 6, message = "Password must be at least 6 characters")
        private String password;

        // 참고: id, createdAt, updatedAt는 클라이언트가 보내면 안 되므로 포함 안 함
    }

    // ============================================
    // Response DTO: 서버 → 클라이언트 (출력 데이터)
    // ============================================
    /**
     * 사용자 정보 응답 DTO
     *
     * 사용 시나리오:
     * - 회원가입 성공 응답 (POST /api/users)
     * - 회원정보 조회 (GET /api/users/{id})
     * - 회원 목록 조회 (GET /api/users)
     *
     * 예시 JSON:
     * {
     *   "id": 1,
     *   "username": "john",
     *   "email": "john@example.com",
     *   "createdAt": "2025-11-01T15:30:00",
     *   "updatedAt": "2025-11-01T15:30:00"
     * }
     *
     * 주의: password는 포함하지 않음 (보안)
     */
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {

        private Long id;              // 사용자 고유 ID
        private String username;      // 사용자명
        private String email;         // 이메일
        private LocalDateTime createdAt;  // 생성 일시
        private LocalDateTime updatedAt;  // 수정 일시

        // 주의: password는 응답에 포함하지 않음! (보안 위험)

        /**
         * User 엔티티를 Response DTO로 변환하는 정적 팩토리 메서드
         *
         * 사용 예시:
         * User user = userRepository.findById(1L).orElseThrow();
         * UserDto.Response response = UserDto.Response.from(user);
         * return ResponseEntity.ok(response);
         *
         * 왜 이렇게 하나?
         * - 엔티티를 직접 반환하면 양방향 참조 시 순환 참조 오류 발생 가능
         * - 엔티티의 모든 필드가 노출됨 (password 등 민감 정보)
         * - DTO로 변환하면 필요한 정보만 선택적으로 노출 가능
         *
         * @param user User 엔티티 객체
         * @return UserDto.Response DTO 객체
         */
        public static Response from(User user) {
            return Response.builder()
                    .id(user.getId())
                    .username(user.getUsername())
                    .email(user.getEmail())
                    .createdAt(user.getCreatedAt())
                    .updatedAt(user.getUpdatedAt())
                    // password는 의도적으로 제외 (보안)
                    .build();
        }

        // 참고: 엔티티 → DTO 변환을 MapStruct로 자동화할 수도 있음
        // 하지만 간단한 경우는 위처럼 수동으로 작성하는 것이 더 명확함
    }

    // ============================================
    // 실제 사용 흐름 예시
    // ============================================
    /*
     * 1. 클라이언트가 회원가입 요청
     *    POST /api/users
     *    Body: {"username": "john", "email": "john@...", "password": "1234"}
     *
     * 2. Controller에서 Request DTO 수신
     *    public ResponseEntity<Response> createUser(@Valid @RequestBody Request request)
     *    → @Valid가 @NotBlank, @Email 등 검증 수행
     *
     * 3. Service에서 DTO → Entity 변환
     *    User user = User.builder()
     *        .username(request.getUsername())
     *        .email(request.getEmail())
     *        .password(passwordEncoder.encode(request.getPassword()))
     *        .build();
     *
     * 4. Repository에 저장
     *    User savedUser = userRepository.save(user);
     *
     * 5. Entity → Response DTO 변환
     *    Response response = Response.from(savedUser);
     *
     * 6. 클라이언트에게 응답
     *    return ResponseEntity.ok(response);
     *    → {"id": 1, "username": "john", "email": "john@...", "createdAt": "...", "updatedAt": "..."}
     *    → password는 응답에 포함 안 됨!
     */
}