package com.example.ncc_spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class NccSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(NccSpringApplication.class, args);
	}

}
