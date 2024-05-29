package com.unibank.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class UniTechApplication {

	public static void main(String[] args) {
		SpringApplication.run(UniTechApplication.class, args);
	}

}
