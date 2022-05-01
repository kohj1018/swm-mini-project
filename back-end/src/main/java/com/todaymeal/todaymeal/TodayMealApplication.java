package com.todaymeal.todaymeal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class TodayMealApplication {

	public static void main(String[] args) {
		SpringApplication.run(TodayMealApplication.class, args);
	}

}
