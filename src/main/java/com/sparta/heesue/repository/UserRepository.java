package com.sparta.heesue.repository;

import com.sparta.heesue.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // JpaRepository<Entity클래스, PK타입>

    // 기본 제공 메서드 (자동으로 생긴다):
    // save(User) - 저장
    // findById(Long) - ID로 조회
    // findAll() - 전체 조회
    // deleteById(Long) - 삭제
    // count() - 개수

    // 커스텀 메서드 (필요한 것만 추가)
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}