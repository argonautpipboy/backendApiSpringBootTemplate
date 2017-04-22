package com.backend.springboot.template;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class ApiBackEndApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiBackEndApplication.class, args);
	}
}
