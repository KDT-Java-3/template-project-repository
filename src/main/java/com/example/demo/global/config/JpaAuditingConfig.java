package com.example.demo.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * JPA Auditing 활성화(생성/수정 시각 자동 세팅).
 */
@Configuration
@EnableJpaAuditing
public class JpaAuditingConfig {
}
