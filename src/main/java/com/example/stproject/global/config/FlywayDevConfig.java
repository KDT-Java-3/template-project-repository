package com.example.stproject.global.config;

import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

//@Configuration
public class FlywayDevConfig {
    @Bean
    public FlywayMigrationStrategy cleanMigrateStrategy() {
        // 실행 시 --spring.profiles.active=dev로 띄우면 매번 깨끗이 재적용
        return flyway -> {
            flyway.clean();    // 모든 객체 및 flyway_schema_history 제거
            flyway.migrate();  // V1부터 재적용
        };
    }
}
