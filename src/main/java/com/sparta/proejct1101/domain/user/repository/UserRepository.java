package com.sparta.proejct1101.domain.user.repository;

import com.sparta.proejct1101.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserId(String userid);
}
