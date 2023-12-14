package com.camarones.clubManagment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ClubManagmentApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClubManagmentApplication.class, args);
	}

}
