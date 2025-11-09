package com.sprata.sparta_ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SpartaEcommerceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpartaEcommerceApplication.class, args);
	}

}
