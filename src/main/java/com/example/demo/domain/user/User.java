package com.example.demo.domain.user;

import com.example.demo.domain.purchase.Purchase;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * User 엔티티 클래스
 * JPA를 통해 데이터베이스의 users 테이블과 매핑됩니다.
 */
@Entity  // JPA 엔티티임을 선언. 이 클래스는 데이터베이스 테이블과 매핑됩니다.
@Table(name = "users")  // 매핑될 테이블 이름을 명시적으로 지정 (기본값은 클래스명의 소문자)
@Getter  // Lombok: 모든 필드의 getter 메서드 자동 생성
@Setter  // Lombok: 모든 필드의 setter 메서드 자동 생성
@NoArgsConstructor  // Lombok: 파라미터가 없는 기본 생성자 자동 생성 (*JPA는 기본 생성자를 필수로 요구)
@AllArgsConstructor  // Lombok: 모든 필드를 파라미터로 받는 생성자 자동 생성
@Builder  // Lombok: 빌더 패턴 구현체 자동 생성 (User.builder().username("test").build())
public class User {

    // ===== Primary Key =====
    /**
     * 기본 키 (Primary Key)
     * - @Id: 이 필드가 테이블의 Primary Key임을 나타냅니다.
     * - @GeneratedValue: 기본 키 생성을 데이터베이스에 위임합니다.
     * - GenerationType.IDENTITY: AUTO_INCREMENT를 사용 (MySQL, PostgreSQL 등)
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ===== 일반 필드 =====
    /**
     * 사용자 이름 필드
     * - nullable = false: NOT NULL 제약조건 (필수 값)
     * - unique = true: UNIQUE 제약조건 (중복 불가)
     * - length = 50: VARCHAR(50) 컬럼 타입 지정
     */
    @Column(nullable = false, unique = true, length = 50)
    private String username;

    /**
     * 이메일 필드
     * - nullable = false: NOT NULL 제약조건
     * - unique = true: UNIQUE 제약조건
     * - length = 100: VARCHAR(100) 컬럼 타입 지정
     */
    @Column(nullable = false, unique = true, length = 100)
    private String email;

    /**
     * 비밀번호 필드
     * - nullable = false: NOT NULL 제약조건
     * - length 미지정: 기본값 255 (VARCHAR(255))
     */
    @Column(nullable = false)
    private String password;

    /**
     * 생성 일시 필드
     * - name = "created_at": 컬럼명을 명시적으로 지정 (Java: camelCase → DB: snake_case)
     * - nullable = false: NOT NULL 제약조건
     * - updatable = false: UPDATE 시 이 컬럼은 변경되지 않음 (생성 후 수정 불가)
     */
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * 수정 일시 필드
     * - name = "updated_at": 컬럼명을 명시적으로 지정
     */
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;


    // ===== 양방향 관계 =====
    /**
     * User와 Purchase의 양방향 1:N 관계
     * - 한 사용자는 여러 구매(Purchase)를 할 수 있음
     * - mappedBy: Purchase 엔티티의 userId 필드와 매핑
     * - 읽기 전용: 외래키는 Purchase 쪽에서 관리
     */
    @OneToMany(mappedBy = "userId")
    @Builder.Default
    private List<Purchase> purchases = new ArrayList<>();


    // ===== JPA 라이프사이클 콜백 =====
    /**ㅇ
     * JPA 라이프사이클 콜백 메서드
     * @PrePersist: 엔티티가 처음 저장되기 직전에 자동 실행됩니다.
     * (INSERT 쿼리 실행 전)
     * userRepository.save(user);
     * ↓
     * JPA가 INSERT 쿼리 실행 전에 자동으로 onCreate() 메서드 호출
     * ↓
     * 필드에 값 자동 적용
     * createdAt = 2025-11-01T14:30:00
     * updatedAt = 2025-11-01T14:30:00
     */
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();  // 생성 시간 자동 설정
        updatedAt = LocalDateTime.now();  // 수정 시간 자동 설정
    }

    /**
     * JPA 라이프사이클 콜백 메서드
     * @PreUpdate: 엔티티가 수정될 때 자동 실행됩니다.
     * (UPDATE 쿼리 실행 전)
     */
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();  // 수정 시간 자동 업데이트
    }
}