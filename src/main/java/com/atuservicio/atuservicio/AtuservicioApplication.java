package com.atuservicio.atuservicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

@SpringBootApplication
public class AtuservicioApplication {

	public static void main(String[] args) {
		SpringApplication.run(AtuservicioApplication.class, args);
		/*
		System.out.println(UUID.randomUUID().toString());
		System.out.println(UUID.randomUUID().toString());
		System.out.println(UUID.randomUUID().toString());
		System.out.println(UUID.randomUUID().toString());
		System.out.println(UUID.randomUUID().toString());
		System.out.println(UUID.randomUUID().toString());
		System.out.println(UUID.randomUUID().toString());
		System.out.println(UUID.randomUUID().toString());
		System.out.println(UUID.randomUUID().toString());
		System.out.println(UUID.randomUUID().toString());
		System.out.println(UUID.randomUUID().toString());
*/


	}
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Bean
	public CommandLineRunner createPasswordCommand(){
		return args -> {
			System.out.println(passwordEncoder.encode("pass123"));
			System.out.println(passwordEncoder.encode("pass123"));
			System.out.println(passwordEncoder.encode("pass123"));
			System.out.println(passwordEncoder.encode("pass123"));
			System.out.println(passwordEncoder.encode("pass123"));
			System.out.println(passwordEncoder.encode("pass123"));
			System.out.println(passwordEncoder.encode("pass123"));
		};
	}

}
