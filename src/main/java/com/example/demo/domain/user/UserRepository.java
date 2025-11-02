package com.example.demo.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * User 엔티티의 Repository 인터페이스
 *
 * Spring Data JPA를 사용하여 데이터베이스 CRUD 작업을 자동으로 처리합니다.
 * JpaRepository를 상속받으면 기본적인 CRUD 메서드가 자동으로 제공됩니다.
 *
 * @see JpaRepository - save(), findById(), findAll(), delete() 등의 기본 메서드 제공
 */
@Repository  // Spring의 Repository Bean으로 등록 (생략 가능하지만 명시적으로 표현)
public interface UserRepository extends JpaRepository<User, Long> {
    // JpaRepository<엔티티 타입, ID 타입>
    // - User: 이 Repository가 관리할 엔티티 클래스
    // - Long: User 엔티티의 ID(@Id) 필드 타입

    // ============================================
    // 기본 제공 메서드 (JpaRepository로부터 자동 상속)
    // ============================================
    // - save(User user): INSERT 또는 UPDATE
    // - findById(Long id): ID로 조회 (Optional<User> 반환)
    // - findAll(): 전체 조회 (List<User> 반환)
    // - deleteById(Long id): ID로 삭제
    // - count(): 전체 개수
    // - existsById(Long id): ID 존재 여부
    // ... 등 약 20개의 기본 메서드 자동 제공

    // ============================================
    // 커스텀 쿼리 메서드 (Query Method)
    // ============================================
    // Spring Data JPA는 메서드 이름만으로 쿼리를 자동 생성합니다.
    // 명명 규칙: findBy필드명, existsBy필드명, countBy필드명 등

    /**
     * username으로 User 조회
     *
     * 자동 생성되는 쿼리:
     * SELECT * FROM users WHERE username = ?
     *
     * @param username 조회할 사용자명
     * @return Optional<User> - 사용자가 존재하면 User 객체, 없으면 Optional.empty()
     *
     * 사용 예시:
     * Optional<User> user = userRepository.findByUsername("john");
     * if (user.isPresent()) {
     *     User foundUser = user.get();
     * }
     */
    Optional<User> findByUsername(String username);

    /**
     * email로 User 조회
     *
     * 자동 생성되는 쿼리:
     * SELECT * FROM users WHERE email = ?
     *
     * @param email 조회할 이메일 주소
     * @return Optional<User> - 사용자가 존재하면 User 객체, 없으면 Optional.empty()
     *
     * 사용 예시:
     * userRepository.findByEmail("john@example.com")
     *     .ifPresent(user -> System.out.println(user.getUsername()));
     */
    Optional<User> findByEmail(String email);

    /**
     * username 존재 여부 확인
     *
     * 자동 생성되는 쿼리:
     * SELECT COUNT(*) > 0 FROM users WHERE username = ?
     *
     * @param username 확인할 사용자명
     * @return boolean - 존재하면 true, 없으면 false
     *
     * 사용 예시:
     * if (userRepository.existsByUsername("john")) {
     *     throw new DuplicateUsernameException("이미 사용 중인 사용자명입니다.");
     * }
     */
    boolean existsByUsername(String username);

    /**
     * email 존재 여부 확인
     *
     * 자동 생성되는 쿼리:
     * SELECT COUNT(*) > 0 FROM users WHERE email = ?
     *
     * @param email 확인할 이메일 주소
     * @return boolean - 존재하면 true, 없으면 false
     *
     * 사용 예시:
     * if (userRepository.existsByEmail("john@example.com")) {
     *     throw new DuplicateEmailException("이미 사용 중인 이메일입니다.");
     * }
     */
    boolean existsByEmail(String email);

    // ============================================
    // 추가 가능한 쿼리 메서드 예시
    // ============================================
    // List<User> findByUsernameContaining(String keyword); // LIKE 검색
    // List<User> findByCreatedAtBefore(LocalDateTime date); // 날짜 이전
    // List<User> findByCreatedAtAfter(LocalDateTime date);  // 날짜 이후
    // Page<User> findAll(Pageable pageable);                // 페이징 처리
    // List<User> findTop10ByOrderByCreatedAtDesc();         // 상위 10개, 생성일 내림차순

    // ============================================
    // @Query 어노테이션을 사용한 커스텀 쿼리 예시
    // ============================================
    // @Query("SELECT u FROM User u WHERE u.username = :username AND u.email = :email")
    // Optional<User> findByUsernameAndEmail(@Param("username") String username, @Param("email") String email);

    // @Query(value = "SELECT * FROM users WHERE created_at > NOW() - INTERVAL 7 DAY", nativeQuery = true)
    // List<User> findRecentUsers();
}