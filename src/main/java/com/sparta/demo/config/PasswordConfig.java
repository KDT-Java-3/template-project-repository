package com.sparta.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 비밀번호 암호화 설정
 */
@Configuration
public class PasswordConfig {

    /**
     * BCrypt 알고리즘을 사용하는 PasswordEncoder Bean 정의
     * - 단방향 암호화로 비밀번호를 안전하게 저장
     * - Salt를 자동으로 생성하여 같은 비밀번호도 다른 해시값으로 저장
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
