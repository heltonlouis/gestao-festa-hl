package com.hame.festa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class GestaoFestaApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestaoFestaApplication.class, args);
		// System.out.println(new BCryptPasswordEncoder().encode("123"));
	}

}
