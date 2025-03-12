package com.example.accounts_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.example.accounts_service.repository")
public class AccountsServiceApplication {


	public static void main(String[] args) {
		SpringApplication.run(AccountsServiceApplication.class, args);
	}

}
