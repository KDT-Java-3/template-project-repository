package com.sparta.proejct1101;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class Proejct1101Application {

	public static void main(String[] args) {
		SpringApplication.run(Proejct1101Application.class, args);
	}

}
