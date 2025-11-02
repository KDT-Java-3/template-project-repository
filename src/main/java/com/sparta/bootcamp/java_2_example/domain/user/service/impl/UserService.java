package com.sparta.bootcamp.java_2_example.domain.user.service.impl;

import org.springframework.stereotype.Service;

import com.sparta.bootcamp.java_2_example.domain.user.service.UserCommandService;
import com.sparta.bootcamp.java_2_example.domain.user.service.UserQueryService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author : leeyounggyo
 * @package : com.sparta.bootcamp.java_2_example.domain.user.service
 * @since : 2025. 11. 2.
 */
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserService implements UserQueryService, UserCommandService {

}
