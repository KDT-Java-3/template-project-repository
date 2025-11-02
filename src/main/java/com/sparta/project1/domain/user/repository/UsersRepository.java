package com.sparta.project1.domain.user.repository;

import com.sparta.project1.domain.user.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, Long> {
}
