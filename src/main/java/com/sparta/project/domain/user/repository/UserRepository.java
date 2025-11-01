package com.sparta.project.domain.user.repository;

import com.sparta.project.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

// JpaRepository<T, ID>
// T: 리포지토리에서 다룰 엔티티 클래스 (예: User)
// ID: 해당 엔티티의 Primary Key 필드 타입 (예: Long)
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // 이 안에 코드가 없어도 아래의 주요 메서드들을 바로 주입받아 사용할 수 있습니다.

    // 이메일로 유저 조회 (결과가 없을 수 있으므로 Optional 사용)
    Optional<User> findByEmail(String email);

    // 특정 날짜 이후에 가입한 유저들을 이름 순으로 정렬하여 조회
    List<User> findByCreatedAtAfterOrderByUsernameAsc(LocalDateTime dateTime);

    // 'name'이 같은 유저의 수를 카운트
    long countByUsername(String name);
}