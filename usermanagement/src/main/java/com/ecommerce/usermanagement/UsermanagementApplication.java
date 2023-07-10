package com.ecommerce.usermanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class UsermanagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(UsermanagementApplication.class, args);
	}

}
