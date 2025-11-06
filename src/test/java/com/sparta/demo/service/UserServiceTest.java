package com.sparta.demo.service;

import com.sparta.demo.domain.user.User;
import com.sparta.demo.domain.user.UserStatus;
import com.sparta.demo.exception.ServiceException;
import com.sparta.demo.exception.ServiceExceptionCode;
import com.sparta.demo.repository.UserRepository;
import com.sparta.demo.service.dto.user.UserCreateDto;
import com.sparta.demo.service.dto.user.UserDto;
import com.sparta.demo.service.mapper.UserServiceMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

/**
 * UserService 단위 테스트
 * Mockito를 사용한 단위 테스트로 외부 의존성(DB, 암호화)을 Mock 처리
 */
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserServiceMapper mapper;

    @InjectMocks
    private UserService userService;

    @Test
    @DisplayName("사용자 생성 성공 - 주소 포함")
    void createUser_WithAddress_Success() {
        // Given: 주소를 포함한 사용자 생성 요청 데이터가 주어졌을 때
        UserCreateDto createDto = new UserCreateDto(
                "testuser",
                "test@example.com",
                "password123",
                "서울시 강남구"
        );

        String encodedPassword = "encodedPassword123";
        User savedUser = User.builder()
                .id(1L)
                .username("testuser")
                .email("test@example.com")
                .passwordHash(encodedPassword)
                .address("서울시 강남구")
                .status(UserStatus.ACTIVE)
                .build();

        UserDto expectedDto = new UserDto(
                1L,
                "testuser",
                "test@example.com",
                "서울시 강남구",
                UserStatus.ACTIVE,
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        // Mock 동작 설정
        given(userRepository.findByEmail(createDto.getEmail())).willReturn(Optional.empty());
        given(userRepository.findByUsername(createDto.getUsername())).willReturn(Optional.empty());
        given(passwordEncoder.encode(createDto.getPassword())).willReturn(encodedPassword);
        given(userRepository.save(any(User.class))).willReturn(savedUser);
        given(mapper.toDto(savedUser)).willReturn(expectedDto);

        // When: 사용자 생성 메서드를 호출하면
        UserDto result = userService.createUser(createDto);

        // Then: 사용자가 정상적으로 생성되고 DTO가 반환된다
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getUsername()).isEqualTo("testuser");
        assertThat(result.getEmail()).isEqualTo("test@example.com");
        assertThat(result.getAddress()).isEqualTo("서울시 강남구");
        assertThat(result.getStatus()).isEqualTo(UserStatus.ACTIVE);

        // 검증: 각 메서드가 올바르게 호출되었는지 확인
        verify(userRepository).findByEmail(createDto.getEmail());
        verify(userRepository).findByUsername(createDto.getUsername());
        verify(passwordEncoder).encode(createDto.getPassword());
        verify(userRepository).save(any(User.class));
        verify(mapper).toDto(savedUser);
    }

    @Test
    @DisplayName("사용자 생성 성공 - 주소 없음")
    void createUser_WithoutAddress_Success() {
        // Given: 주소가 없는 사용자 생성 요청 데이터가 주어졌을 때
        UserCreateDto createDto = new UserCreateDto(
                "testuser",
                "test@example.com",
                "password123",
                null  // 주소 없음
        );

        String encodedPassword = "encodedPassword123";
        User savedUser = User.builder()
                .id(1L)
                .username("testuser")
                .email("test@example.com")
                .passwordHash(encodedPassword)
                .status(UserStatus.ACTIVE)
                .build();

        UserDto expectedDto = new UserDto(
                1L,
                "testuser",
                "test@example.com",
                null,
                UserStatus.ACTIVE,
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        // Mock 동작 설정
        given(userRepository.findByEmail(createDto.getEmail())).willReturn(Optional.empty());
        given(userRepository.findByUsername(createDto.getUsername())).willReturn(Optional.empty());
        given(passwordEncoder.encode(createDto.getPassword())).willReturn(encodedPassword);
        given(userRepository.save(any(User.class))).willReturn(savedUser);
        given(mapper.toDto(savedUser)).willReturn(expectedDto);

        // When: 사용자 생성 메서드를 호출하면
        UserDto result = userService.createUser(createDto);

        // Then: 주소 없이 사용자가 정상적으로 생성된다
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getUsername()).isEqualTo("testuser");
        assertThat(result.getAddress()).isNull();

        verify(userRepository).save(any(User.class));
    }

    @Test
    @DisplayName("사용자 생성 실패 - 이메일 중복")
    void createUser_DuplicateEmail_ThrowsException() {
        // Given: 이미 존재하는 이메일로 사용자 생성 요청이 주어졌을 때
        UserCreateDto createDto = new UserCreateDto(
                "testuser",
                "duplicate@example.com",
                "password123",
                "서울시 강남구"
        );

        User existingUser = User.builder()
                .id(999L)
                .username("otheruser")
                .email("duplicate@example.com")
                .passwordHash("someHash")
                .build();

        // Mock 동작 설정: 이메일이 이미 존재함
        given(userRepository.findByEmail(createDto.getEmail())).willReturn(Optional.of(existingUser));

        // When & Then: 사용자 생성 시도 시 DUPLICATE_EMAIL 예외가 발생한다
        assertThatThrownBy(() -> userService.createUser(createDto))
                .isInstanceOf(ServiceException.class)
                .hasFieldOrPropertyWithValue("exceptionCode", ServiceExceptionCode.DUPLICATE_EMAIL);

        // 검증: 이메일 중복 확인 후 더 이상 진행되지 않음
        verify(userRepository).findByEmail(createDto.getEmail());
        verify(userRepository, never()).findByUsername(anyString());
        verify(passwordEncoder, never()).encode(anyString());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    @DisplayName("사용자 생성 실패 - 사용자명 중복")
    void createUser_DuplicateUsername_ThrowsException() {
        // Given: 이미 존재하는 사용자명으로 사용자 생성 요청이 주어졌을 때
        UserCreateDto createDto = new UserCreateDto(
                "duplicateuser",
                "test@example.com",
                "password123",
                "서울시 강남구"
        );

        User existingUser = User.builder()
                .id(999L)
                .username("duplicateuser")
                .email("other@example.com")
                .passwordHash("someHash")
                .build();

        // Mock 동작 설정: 이메일은 사용 가능하지만 사용자명이 중복됨
        given(userRepository.findByEmail(createDto.getEmail())).willReturn(Optional.empty());
        given(userRepository.findByUsername(createDto.getUsername())).willReturn(Optional.of(existingUser));

        // When & Then: 사용자 생성 시도 시 DUPLICATE_USERNAME 예외가 발생한다
        assertThatThrownBy(() -> userService.createUser(createDto))
                .isInstanceOf(ServiceException.class)
                .hasFieldOrPropertyWithValue("exceptionCode", ServiceExceptionCode.DUPLICATE_USERNAME);

        // 검증: 사용자명 중복 확인 후 더 이상 진행되지 않음
        verify(userRepository).findByEmail(createDto.getEmail());
        verify(userRepository).findByUsername(createDto.getUsername());
        verify(passwordEncoder, never()).encode(anyString());
        verify(userRepository, never()).save(any(User.class));
    }
}
