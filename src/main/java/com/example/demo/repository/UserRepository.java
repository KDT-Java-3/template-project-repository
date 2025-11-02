package com.example.demo.repository;

import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // 이메일로 유저 조회 (결과가 없을 수 있으므로 Optional 사용)
    Optional<User> findByEmail(String email);

    // 특정 날짜 이후에 가입한 유저들을 이름 순으로 정렬하여 조회
    List<User> findByCreatedAtAfterOrderByUsernameAsc(LocalDateTime dateTime);

    List<User> findByUsername(String username);

    // 'username'이 같은 유저의 수를 카운트
    long countByUsername(String username);


    // 이메일 주소는 고유(unique)하므로, 조회 결과는 단일 객체입니다.
    // 결과가 없을 수도 있는 상황을 고려하여 Optional<User>로 반환하는 것이 안전합니다.
    @Query("SELECT u FROM User u WHERE u.email = :email")
    Optional<User> findUserByEmail(@Param("email") String email);


}