package com.maksymenko.wp_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class WpBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(WpBackendApplication.class, args);
	}

}
