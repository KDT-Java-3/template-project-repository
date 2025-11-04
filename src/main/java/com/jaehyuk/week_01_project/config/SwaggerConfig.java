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
                description = "클라우드 융합 자바 개발 업스킬링 과정 3기",
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
}