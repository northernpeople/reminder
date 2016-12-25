package com.reminder;

import nz.net.ultraq.thymeleaf.LayoutDialect;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class ReminderApplication {
	public static void main(String[] args) {
		SpringApplication.run(ReminderApplication.class, args);
	}
	
	@Bean
	public LayoutDialect layoutDialect() {
		return new LayoutDialect();
	}
}
