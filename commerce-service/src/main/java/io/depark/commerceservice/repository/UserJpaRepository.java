package io.depark.commerceservice.repository;

import io.depark.commerceservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<User, Long> {
}
