package com.jaehyuk.week_01_project.config;

import com.jaehyuk.week_01_project.config.auth.AuthInterceptor;
import com.jaehyuk.week_01_project.config.auth.LoginUserArgumentResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final AuthInterceptor authInterceptor;
    private final LoginUserArgumentResolver loginUserArgumentResolver;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor)
                .order(1)
                .addPathPatterns("/api/**")  // 모든 API에 대해 인증 체크
                .excludePathPatterns(
                        "/api/user/v1/signup",    // 회원가입은 인증 불필요
                        "/api/user/v1/login",     // 로그인은 인증 불필요
                        "/swagger-ui/**",         // Swagger UI
                        "/v3/api-docs/**",        // Swagger API docs
                        "/actuator/**"            // Actuator
                );
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(loginUserArgumentResolver);
    }
}