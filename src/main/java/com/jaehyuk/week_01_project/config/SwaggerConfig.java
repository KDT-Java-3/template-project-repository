package com.jaehyuk.week_01_project.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                title = "재직자 JAVA 프로젝트 1주차",
                description = """
                        클라우드 융합 자바 개발 업스킬링 과정 3기

                        ## 인증 방법
                        1. `/api/user/v1/login` API를 먼저 호출하여 로그인
                        2. 로그인 성공 시 세션 쿠키가 자동으로 저장됨
                        3. 이후 다른 API 호출 시 자동으로 인증됨
                        4. 로그아웃은 `/api/user/v1/logout` 호출

                        **주의**: Swagger UI는 브라우저의 쿠키를 자동으로 관리하므로 별도 설정 없이 테스트 가능합니다.
                        """,
                version = "v1",
                contact = @Contact(
                        name = "이재혁",
                        email = "jaehyuk.dev@gmail.com",
                        url = "https://github.com/jaehyuk-dev"
                )
        )
)
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        // 쿠키 기반 인증 스키마 정의
        SecurityScheme cookieAuth = new SecurityScheme()
                .type(SecurityScheme.Type.APIKEY)
                .in(SecurityScheme.In.COOKIE)
                .name("JSESSIONID")
                .description("세션 쿠키 인증 (로그인 후 자동으로 설정됨)");

        // 보안 요구사항 정의
        SecurityRequirement securityRequirement = new SecurityRequirement()
                .addList("cookieAuth");

        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("cookieAuth", cookieAuth))
                .addSecurityItem(securityRequirement);
    }
}