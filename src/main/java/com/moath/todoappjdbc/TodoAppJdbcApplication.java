package com.moath.todoappjdbc;

import com.moath.todoappjdbc.config.SecurityConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableAsync
public class TodoAppJdbcApplication {

	public static void main(String[] args) {
		SpringApplication.run(TodoAppJdbcApplication.class, args);
	}

}
