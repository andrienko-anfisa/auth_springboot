package ru.andrienko.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AuthApplication {

	public static void main(String[] args) {
		try {
		SpringApplication.run(AuthApplication.class, args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
