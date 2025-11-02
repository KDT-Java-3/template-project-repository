package com.example.demo.domain.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * User 비즈니스 로직 처리 Service
 *
 * 역할: 비즈니스 로직 처리, 트랜잭션 관리, DTO ↔ Entity 변환
 * 계층: Business Logic Layer (비즈니스 로직 계층)
 *
 * 주요 책임:
 * 1. 비즈니스 규칙 검증 (중복 체크 등)
 * 2. 트랜잭션 관리
 * 3. DTO ↔ Entity 변환
 * 4. Repository 호출
 */
@Service  // Spring Bean으로 등록 (비즈니스 로직 계층)
@RequiredArgsConstructor  // final 필드 생성자 자동 생성
@Transactional(readOnly = true)  // 클래스 레벨: 모든 메서드에 읽기 전용 트랜잭션 적용 (성능 최적화)
public class UserService {

    // ===== 의존성 주입 =====
    private final UserRepository userRepository;  // JPA Repository (DB 접근)

    // ===== 1. 사용자 생성 (회원가입) =====
    /**
     * 새 사용자 생성
     *
     * 비즈니스 로직:
     * 1. username 중복 체크
     * 2. email 중복 체크
     * 3. DTO → Entity 변환
     * 4. DB 저장
     * 5. Entity → DTO 변환 후 반환
     *
     * @param request 사용자 생성 요청 DTO
     * @return 생성된 사용자 응답 DTO
     * @throws IllegalArgumentException username 또는 email이 이미 존재하는 경우
     */
    @Transactional  // 메서드 레벨: 쓰기 트랜잭션 (클래스 레벨 readOnly를 오버라이드)
    public UserDto.Response createUser(UserDto.Request request) {
        // 비즈니스 규칙 검증: username 중복 체크
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }

        // 비즈니스 규칙 검증: email 중복 체크
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        // DTO → Entity 변환 (빌더 패턴 사용)
        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(request.getPassword())  // TODO: Encrypt password (BCrypt 등)
                .build();

        // Repository를 통해 DB에 저장
        // @PrePersist가 자동으로 createdAt, updatedAt 설정
        User savedUser = userRepository.save(user);

        // Entity → Response DTO 변환 후 반환
        return UserDto.Response.from(savedUser);
    }

    // ===== 2. ID로 사용자 조회 =====
    /**
     * ID로 사용자 조회
     *
     * @param id 사용자 ID
     * @return 조회된 사용자 응답 DTO
     * @throws IllegalArgumentException 사용자가 존재하지 않는 경우
     */
    public UserDto.Response getUserById(Long id) {
        // Repository에서 ID로 조회
        // Optional 사용: 없으면 예외 발생
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + id));

        // Entity → Response DTO 변환
        return UserDto.Response.from(user);
    }

    // ===== 3. Username으로 사용자 조회 =====
    /**
     * Username으로 사용자 조회
     *
     * @param username 사용자명
     * @return 조회된 사용자 응답 DTO
     * @throws IllegalArgumentException 사용자가 존재하지 않는 경우
     */
    public UserDto.Response getUserByUsername(String username) {
        // Repository의 Query Method로 조회
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found with username: " + username));
        return UserDto.Response.from(user);
    }

    // ===== 4. 전체 사용자 목록 조회 =====
    /**
     * 전체 사용자 목록 조회
     *
     * @return 사용자 응답 DTO 리스트
     */
    public List<UserDto.Response> getAllUsers() {
        // Repository에서 전체 조회
        return userRepository.findAll().stream()
                .map(UserDto.Response::from)  // 각 User → Response DTO 변환 (메서드 참조)
                .collect(Collectors.toList());  // List로 수집
    }

    // ===== 5. 사용자 정보 수정 =====
    /**
     * 사용자 정보 수정
     *
     * 비즈니스 로직:
     * 1. ID로 기존 사용자 조회
     * 2. 변경하려는 username/email이 다른 사용자가 사용 중인지 체크
     * 3. 필드 업데이트
     * 4. DB 저장
     *
     * @param id 수정할 사용자 ID
     * @param request 수정할 데이터 DTO
     * @return 수정된 사용자 응답 DTO
     * @throws IllegalArgumentException 사용자가 존재하지 않거나 중복된 경우
     */
    @Transactional  // 쓰기 트랜잭션
    public UserDto.Response updateUser(Long id, UserDto.Request request) {
        // 1. 기존 사용자 조회
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + id));

        // 2. 비즈니스 규칙 검증: 변경하려는 username이 다른 사용자가 사용 중인지 체크
        if (!user.getUsername().equals(request.getUsername())  // username이 변경되었고
                && userRepository.existsByUsername(request.getUsername())) {  // 다른 사용자가 사용 중이면
            throw new IllegalArgumentException("Username already exists");
        }

        // 3. 비즈니스 규칙 검증: 변경하려는 email이 다른 사용자가 사용 중인지 체크
        if (!user.getEmail().equals(request.getEmail())
                && userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        // 4. 필드 업데이트 (Setter 사용)
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());  // TODO: Encrypt password

        // 5. DB 저장
        // @PreUpdate가 자동으로 updatedAt 갱신
        User updatedUser = userRepository.save(user);

        // Entity → Response DTO 변환
        return UserDto.Response.from(updatedUser);
    }

    // ===== 6. 사용자 삭제 =====
    /**
     * 사용자 삭제
     *
     * @param id 삭제할 사용자 ID
     * @throws IllegalArgumentException 사용자가 존재하지 않는 경우
     */
    @Transactional  // 쓰기 트랜잭션
    public void deleteUser(Long id) {
        // 존재 여부 체크 (삭제 전 검증)
        if (!userRepository.existsById(id)) {
            throw new IllegalArgumentException("User not found with id: " + id);
        }

        // Repository를 통해 DB에서 삭제
        userRepository.deleteById(id);
    }

    // ============================================
    // @Transactional 정리
    // ============================================
    // @Transactional(readOnly = true)  ← 클래스 레벨 (기본값)
    // - 읽기 전용 트랜잭션: 성능 최적화 (Dirty Checking 비활성화)
    // - SELECT 쿼리만 실행하는 메서드에 적합
    //
    // @Transactional  ← 메서드 레벨 (쓰기 작업)
    // - readOnly = false (기본값)
    // - INSERT, UPDATE, DELETE 쿼리 실행
    // - 클래스 레벨 설정을 오버라이드
    //
    // 트랜잭션의 역할:
    // - 원자성(Atomicity): 모두 성공하거나 모두 실패
    // - 일관성(Consistency): 데이터 무결성 유지
    // - 격리성(Isolation): 동시 접근 제어
    // - 지속성(Durability): 영구 저장
}