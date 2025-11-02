package com.example.demo.domain.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * User REST API Controller
 *
 * 역할: 클라이언트의 HTTP 요청을 받아서 Service로 전달하고, 응답을 반환
 * 계층: Presentation Layer (프레젠테이션 계층)
 *
 * 주요 책임:
 * 1. HTTP 요청/응답 처리
 * 2. 입력 데이터 검증 (@Valid)
 * 3. Service 호출
 * 4. HTTP 상태 코드 설정
 */
@RestController  // @Controller + @ResponseBody: JSON 응답을 자동으로 반환
@RequestMapping("/api/users")  // 이 컨트롤러의 모든 메서드는 /api/users로 시작
@RequiredArgsConstructor  // final 필드를 파라미터로 받는 생성자 자동 생성 (의존성 주입)
@Tag(name = "User", description = "User management APIs")  // Swagger 문서화: API 그룹 정의
public class UserController {

    // ===== 의존성 주입 =====
    /**
     * UserService 의존성
     * - final: 불변 (한 번 주입되면 변경 불가)
     * - @RequiredArgsConstructor가 생성자 주입 자동 처리
     *
     * 생성자 주입 방식 (권장):
     * public UserController(UserService userService) {
     *     this.userService = userService;
     * }
     */
    private final UserService userService;

    // ===== 1. 사용자 생성 (회원가입) =====
    /**
     * POST /api/users - 새 사용자 생성
     *
     * 요청 예시:
     * POST /api/users
     * {
     *   "username": "john",
     *   "email": "john@example.com",
     *   "password": "password123"
     * }
     *
     * 응답 예시 (201 Created):
     * {
     *   "id": 1,
     *   "username": "john",
     *   "email": "john@example.com",
     *   "createdAt": "2025-11-01T15:30:00",
     *   "updatedAt": "2025-11-01T15:30:00"
     * }
     */
    @PostMapping  // HTTP POST 메서드 매핑
    @Operation(summary = "Create a new user", description = "Creates a new user with the provided information") // Swagger 문서화: API 설명
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input or user already exists")
    }) // Swagger 문서화: API Response 설명
    public ResponseEntity<UserDto.Response> createUser(
            // @Valid: DTO 검증, @RequestBody: JSON → 객체 변환
            @Valid @RequestBody UserDto.Request request
    ) {
        // Service 메서드 수행 (→ Repository → JPA 작업) 후, 응답 반환
        UserDto.Response response = userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);  // 201 Created 응답
    }

    // ===== 2. ID로 사용자 조회 =====
    /**
     * GET /api/users/{id} - ID로 사용자 조회
     *
     * 요청 예시: GET /api/users/1
     *
     * 응답 예시 (200 OK):
     * {
     *   "id": 1,
     *   "username": "john",
     *   "email": "john@example.com",
     *   "createdAt": "2025-11-01T15:30:00",
     *   "updatedAt": "2025-11-01T15:30:00"
     * }
     */
    @GetMapping("/{id}")  // HTTP GET, URL 경로에서 {id} 추출
    @Operation(summary = "Get user by ID", description = "Retrieves a user by their ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<UserDto.Response> getUserById(
            @PathVariable Long id  // @PathVariable: URL의 {id}를 파라미터로 받음
    ) {
        UserDto.Response response = userService.getUserById(id);
        return ResponseEntity.ok(response);  // 200 OK 응답
    }

    // ===== 3. Username으로 사용자 조회 =====
    /**
     * GET /api/users/username/{username} - Username으로 사용자 조회
     *
     * 요청 예시: GET /api/users/username/john
     */
    @GetMapping("/username/{username}")
    @Operation(summary = "Get user by username", description = "Retrieves a user by their username")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<UserDto.Response> getUserByUsername(@PathVariable String username) {
        UserDto.Response response = userService.getUserByUsername(username);
        return ResponseEntity.ok(response);
    }

    // ===== 4. 전체 사용자 목록 조회 =====
    /**
     * GET /api/users - 전체 사용자 목록 조회
     *
     * 요청 예시: GET /api/users
     *
     * 응답 예시 (200 OK):
     * [
     *   {"id": 1, "username": "john", ...},
     *   {"id": 2, "username": "jane", ...}
     * ]
     */
    @GetMapping  // HTTP GET, /api/users (경로 변수 없음)
    @Operation(summary = "Get all users", description = "Retrieves a list of all users")
    @ApiResponse(responseCode = "200", description = "Users retrieved successfully")
    public ResponseEntity<List<UserDto.Response>> getAllUsers() {
        List<UserDto.Response> responses = userService.getAllUsers();
        return ResponseEntity.ok(responses);
    }

    // ===== 5. 사용자 정보 수정 =====
    /**
     * PUT /api/users/{id} - 사용자 정보 수정
     *
     * 요청 예시:
     * PUT /api/users/1
     * {
     *   "username": "john_updated",
     *   "email": "john_new@example.com",
     *   "password": "newpassword123"
     * }
     *
     * 응답 예시 (200 OK): 수정된 사용자 정보
     */
    @PutMapping("/{id}")  // HTTP PUT, 전체 수정 (PATCH는 부분 수정)
    @Operation(summary = "Update user", description = "Updates an existing user's information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<UserDto.Response> updateUser(
            @PathVariable Long id,  // URL에서 수정할 사용자 ID
            @Valid @RequestBody UserDto.Request request  // 수정할 데이터
    ) {
        UserDto.Response response = userService.updateUser(id, request);
        return ResponseEntity.ok(response);  // 200 OK 응답
    }

    // ===== 6. 사용자 삭제 =====
    /**
     * DELETE /api/users/{id} - 사용자 삭제
     *
     * 요청 예시: DELETE /api/users/1
     *
     * 응답: 204 No Content (응답 본문 없음)
     */
    @DeleteMapping("/{id}")  // HTTP DELETE
    @Operation(summary = "Delete user", description = "Deletes a user by their ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User deleted successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();  // 204 No Content 응답 (본문 없음)
    }

    // ============================================
    // HTTP 메서드별 용도 정리
    // ============================================
    // GET    : 조회 (Read) - 데이터 변경 없음, 멱등성 O
    // POST   : 생성 (Create) - 새 리소스 생성, 멱등성 X
    // PUT    : 전체 수정 (Update) - 전체 교체, 멱등성 O
    // PATCH  : 부분 수정 (Partial Update) - 일부만 수정, 멱등성 △
    // DELETE : 삭제 (Delete) - 리소스 삭제, 멱등성 O
    //
    // 멱등성(Idempotent): 같은 요청을 여러 번 해도 결과가 같음
    // - GET /users/1 여러 번 → 같은 결과
    // - POST /users 여러 번 → 매번 새 사용자 생성 (결과 다름)
    // - DELETE /users/1 여러 번 → 첫 번째만 삭제, 나머지는 404 (상태는 같음)
}