package com.jaehyuk.week_01_project.config.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * @LoginUser 어노테이션이 붙은 파라미터에 현재 로그인한 사용자의 ID를 주입하는 ArgumentResolver
 */
@Component
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        // @LoginUser 어노테이션이 있고, 타입이 Long인 파라미터만 처리
        return parameter.hasParameterAnnotation(LoginUser.class) &&
                Long.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                   NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        HttpSession session = request.getSession(false);

        if (session == null) {
            return null;
        }

        return session.getAttribute(SessionConst.LOGIN_USER);
    }
}