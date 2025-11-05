package com.jaehyuk.week_01_project.config.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 인증이 필요한 API에 대해 세션을 체크하는 Interceptor
 */
@Slf4j
@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        HttpSession session = request.getSession(false);

        // 세션이 없거나, 세션에 로그인 정보가 없으면 인증 실패
        if (session == null || session.getAttribute(SessionConst.LOGIN_USER) == null) {
            log.warn("미인증 사용자의 요청 - URI: {}", requestURI);

            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"error\": \"인증이 필요합니다. 로그인 후 다시 시도해주세요.\"}");

            return false;
        }

        Long userId = (Long) session.getAttribute(SessionConst.LOGIN_USER);
        log.debug("인증된 사용자 - userId: {}, URI: {}", userId, requestURI);

        return true;
    }
}