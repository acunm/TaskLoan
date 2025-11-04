package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class TaskHubApplication {

	public static void main(String[] args) {
		BCryptPasswordEncoder p = new BCryptPasswordEncoder();
		System.out.println(p.encode("123456"));
		SpringApplication.run(TaskHubApplication.class, args);
	}

}
