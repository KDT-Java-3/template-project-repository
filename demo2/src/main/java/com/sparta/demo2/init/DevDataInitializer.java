package com.sparta.demo2.init;

import com.sparta.demo2.domain.category.entity.Category;
import com.sparta.demo2.domain.category.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Profile("dev") // "dev" 프로파일에서만 이 빈이 활성화됩니다.
@RequiredArgsConstructor
public class DevDataInitializer implements CommandLineRunner {

    // 코드 데이터
    private final CategoryRepository categoryRepository;

    @Override
    public void run(String... args) throws Exception {
        // 개발 환경에서만 실행될 테스트 데이터 삽입 로직
        Category category = Category.builder()
                .name("전자제품")
                .build();

        
        categoryRepository.save(category);
    }
}
