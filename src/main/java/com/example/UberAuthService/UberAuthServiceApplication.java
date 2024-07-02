package com.example.UberAuthService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("com.example.uberprojectentityservice.models")
public class UberAuthServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UberAuthServiceApplication.class, args);
	}
}
