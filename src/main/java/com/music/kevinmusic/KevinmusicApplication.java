package com.music.kevinmusic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@EnableJpaAuditing
@EnableJpaRepositories
@SpringBootApplication
public class KevinmusicApplication {

	public static void main(String[] args) {
		SpringApplication.run(KevinmusicApplication.class, args);
	}

}
