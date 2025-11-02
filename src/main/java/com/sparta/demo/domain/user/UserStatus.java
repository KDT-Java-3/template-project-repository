package com.sparta.demo.domain.user;

public enum UserStatus {
    ACTIVE,      // 활성 사용자
    INACTIVE,    // 비활성 (휴면)
    SUSPENDED,   // 정지
    DELETED,     // 탈퇴
    PENDING      // 이메일 인증 대기
}
