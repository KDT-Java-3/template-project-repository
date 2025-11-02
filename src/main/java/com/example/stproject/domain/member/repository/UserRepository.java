package com.example.stproject.domain.member.repository;

import com.example.stproject.domain.member.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    /*
    주요 기본 제공 메서드
    - `save(S entity)`: 새로운 엔티티는 저장(**INSERT**)하고, 이미 존재하는 엔티티는 병합(**UPDATE**)합니다.
    - `findById(ID id)`: PK를 기준으로 엔티티 하나를 조회합니다. 결과는 `Optional<T>`로 반환되어 Null-safe한 처리를 돕습니다.
    - `findAll()`: 모든 엔티티를 조회합니다. (`List<T>`)
    - `count()`: 엔티티의 총 개수를 반환합니다. (`long`)
    - `delete(T entity)`: 특정 엔티티를 삭제합니다.
    - `deleteById(ID id)`: PK를 기준으로 엔티티를 삭제합니다.
    * */

    // 이메일로 유저 조회 (결과가 없을 수 있으므로 Optional 사용)
    @Query("SELECT u FROM User u WHERE u.email = :email")
    Optional<User> findByEmail(String email);

    // 특정 날짜 이후에 가입한 유저들을 이름 순으로 정렬하여 조회
    List<User> findByCreatedAtAfterOrderByUsernameAsc(LocalDateTime dateTime);

    // 'name'이 같은 유저의 수를 카운트
    long countByUsername(String name);

    // 예시: MySQL의 특정 함수를 사용해야 할 경우
    @Query(value = "SELECT * FROM user WHERE username = ?1", nativeQuery = true)
    User findByUsernameNative(String username);
}
